package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.CustomerDto;
import com.novilabs.brewery.web.model.DistributorDto;
import com.novilabs.brewery.web.model.PubDTO;
import com.novilabs.brewery.web.model.pub.Order;
import com.novilabs.brewery.web.model.pub.OrderState;
import com.novilabs.brewery.web.service.DistributorService;
import com.novilabs.brewery.web.service.PubService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

@Service
public class PubServiceImpl implements PubService {
    private final HashMap<String, PubDTO> allPubs = new HashMap<>();

    private final DistributorService distributorService;

    public PubServiceImpl(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @Override
    public Long addBeerStock(String pubId, String upc, Long count) {
        PubDTO pub = allPubs.get(pubId);
        if (pub == null) {
            throw new IllegalArgumentException(format("Pub with id %s does not exist", pubId));
        }
        ConcurrentHashMap<String, DistributorDto> distributors = pub.getDistributors();
        if (!distributors.containsKey(upc)) {
            throw new RuntimeException("No distributor found for getting stock for beer");
        }
        DistributorDto distributorDto = distributors.get(upc);
        Long remaining = distributorService.getNewBeersFromStock(distributorDto.getId(), upc, count);
        if (remaining > 0) {
            Order unfulfilledOrder = new Order();
            String id = UUID.randomUUID().toString();
            unfulfilledOrder.setId(id);
            unfulfilledOrder.setOrderState(OrderState.PENDING);
            unfulfilledOrder.setRemaining(remaining);
            unfulfilledOrder.setUpc(upc);
            pub.getOrders().put(id, unfulfilledOrder);
        } else {
            Order order = new Order();
            String id = UUID.randomUUID().toString();
            order.setId(id);
            order.setOrderState(OrderState.FULFILLED);
            order.setRemaining(0L);
            order.setUpc(upc);
            pub.getOrders().put(id, order);
            order.setRemaining(0L);
        }
        ConcurrentHashMap<String, Long> pubStock = pub.getPubStock();
        long restockValue = count - remaining;
        pubStock.compute(upc, (upcId, beerCount) -> {
            if (beerCount != null) {
                return restockValue + beerCount;
            }
            return restockValue;
        });
        return remaining;
    }

    @Override
    public Long sellBeerToCustomer(String pubId, String beerId, CustomerDto customerDto, Long count) {
        PubDTO pub = allPubs.get(pubId);
        if (pub == null) {
            throw new IllegalArgumentException(format("Pub with id %s does not exist", pubId));
        }
        ConcurrentHashMap<String, Long> pubStock = pub.getPubStock();
        pubStock.putIfAbsent(beerId, 0L);
        Long allBeersOfThatType = pubStock.get(beerId);
        Long availableToSell = Math.min(allBeersOfThatType, count);
        pubStock.put(beerId, Math.max(allBeersOfThatType - availableToSell, 0L));
        return availableToSell;
    }

    public PubDTO createPub(String name) {
        PubDTO pubDTO = new PubDTO();
        String id = UUID.randomUUID().toString();
        pubDTO.setId(id);
        pubDTO.setName(name);
        Map<String, DistributorDto> allDistributors = distributorService.getAllDistributors();
        pubDTO.getDistributors().putAll(allDistributors);
        allPubs.put(id, pubDTO);
        return pubDTO;
    }

    @KafkaListener(topics = "distributorHasNewStockTwo", groupId = "groupOne", containerFactory = "distributorHasNewStockKafkaListenerContainerFactory")
    public void handleDistributorHasNewStockEvent(DistributorHasStockEvent event) {
        for (Map.Entry<String, PubDTO> stringPubDTOEntry : allPubs.entrySet()) {
            PubDTO pub = stringPubDTOEntry.getValue();
            ConcurrentHashMap<String, DistributorDto> distributors = pub.getDistributors();
            if (distributors.containsKey(event.getUpc())) {
                ConcurrentHashMap<String, Order> orders = pub.getOrders();
                for (Map.Entry<String, Order> stringOrderEntry : orders.entrySet()) {
                    Order order = stringOrderEntry.getValue();
                    if (order.getOrderState() == OrderState.PENDING && order.getUpc().equals(event.getUpc())) {
                        Long remaining = distributorService.getNewBeersFromStock(event.getDistributorId(), event.getUpc(), order.getRemaining());
                        if (remaining == 0L) {
                            order.setOrderState(OrderState.FULFILLED);
                            ConcurrentHashMap<String, Long> pubStock = pub.getPubStock();
                            pubStock.compute(event.getUpc(), (upcId, beerCount) -> {
                                if (beerCount != null) {
                                    return order.getRemaining() + beerCount;
                                }
                                return order.getRemaining();
                            });
                            order.setRemaining(0L);
                        }
                    }
                }
            }
        }
    }

    @KafkaListener(topics = "restockEventNotFulfilled", groupId = "groupOne", containerFactory = "restockFailedKafkaListenerContainerFactory")
    public void handleDistributorCannotProvideNewStockEvent(DistributorCannotProvideStockAnymoreEvent event) {
        for (Map.Entry<String, PubDTO> stringPubDTOEntry : allPubs.entrySet()) {
            PubDTO pub = stringPubDTOEntry.getValue();
            ConcurrentHashMap<String, DistributorDto> distributors = pub.getDistributors();
            if (distributors.containsKey(event.getUpc())) {
                ConcurrentHashMap<String, Order> orders = pub.getOrders();
                for (Map.Entry<String, Order> stringOrderEntry : orders.entrySet()) {
                    Order order = stringOrderEntry.getValue();
                    if (order.getOrderState() == OrderState.PENDING && order.getUpc().equals(event.getUpc())) {
                        order.setOrderState(OrderState.UNFULFILLED);
                    }
                }
            }
        }
    }
}
