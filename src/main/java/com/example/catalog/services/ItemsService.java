package com.example.catalog.services;

import com.example.catalog.entity.Item;
import com.example.catalog.entity.Money;
import com.example.catalog.entity.Restaurant;
import com.example.catalog.models.responses.ItemResponse;
import com.example.catalog.repositories.ItemRepository;
import com.example.catalog.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    public String addItem(String name, Money price, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        Item item = new Item(name, price, restaurant);
        restaurant.addItem(item);
        itemRepository.save(item);
        return "Created an Item with id: " + item.getId();
    }
    public List<ItemResponse> fetchAllItems(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        List<ItemResponse> responses = new ArrayList<>();
        for (Item item : restaurant.getMenu()) {
            responses.add(new ItemResponse(item.getId(), item.getName(), item.getPrice()));
        }
        return responses;
    }
    public ItemResponse fetchItem(Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        return new ItemResponse(itemId, item.getName(), item.getPrice());
    }
}
