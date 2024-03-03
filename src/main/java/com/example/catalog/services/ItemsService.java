package com.example.catalog.services;

import com.example.catalog.entities.Item;
import com.example.catalog.entities.Money;
import com.example.catalog.entities.Restaurant;
import com.example.catalog.exceptions.ItemNotFoundException;
import com.example.catalog.models.responses.ItemResponse;
import com.example.catalog.repositories.ItemRepository;
import com.example.catalog.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ItemsService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    public String create(String name, Money price, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        Item item = new Item(name, price, restaurant);
        restaurant.addItem(item);
        itemRepository.save(item);
        return "Created an Item with id: " + item.getId();
    }
    public List<ItemResponse> fetchAll(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        List<ItemResponse> responses = new ArrayList<>();
        for (Item item : restaurant.getMenu()) {
            responses.add(new ItemResponse(item.getId(), item.getName(), item.getPrice()));
        }
        return responses;
    }
    public ItemResponse fetch(Long restaurantId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item with id " + itemId + " not found"));
        if (!Objects.equals(item.getRestaurant().getId(), restaurantId)) {
            throw new ItemNotFoundException("Item " + item.getName() + " not found in Restaurant " + item.getRestaurant().getName());
        }
        return new ItemResponse(itemId, item.getName(), item.getPrice());
    }
}
