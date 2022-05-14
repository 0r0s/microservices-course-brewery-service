package com.novilabs.brewery.service;

import com.novilabs.brewery.web.model.CustomerDto;
import com.novilabs.brewery.web.model.DistributorDto;
import com.novilabs.brewery.web.model.pub.Order;
import com.novilabs.brewery.web.model.pub.OrderState;
import com.novilabs.brewery.web.model.PubDTO;
import com.novilabs.brewery.web.service.DistributorService;
import com.novilabs.brewery.web.service.PubService;
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
            pub.getOrders().put(id, unfulfilledOrder);
        } else {
            Order unfulfilledOrder = new Order();
            String id = UUID.randomUUID().toString();
            unfulfilledOrder.setId(id);
            unfulfilledOrder.setOrderState(OrderState.FULFILLED);
            pub.getOrders().put(id, unfulfilledOrder);
        }
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
}
