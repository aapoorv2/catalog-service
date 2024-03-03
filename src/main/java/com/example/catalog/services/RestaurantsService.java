package com.example.catalog.services;

import com.example.catalog.entities.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.repositories.RestaurantRepository;
import com.example.catalog.models.responses.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantsService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public String create(String name, City city) {
        Restaurant restaurant = new Restaurant(name, city);
        restaurant = restaurantRepository.save(restaurant);
        return "Created a Restaurant with id: " + restaurant.getId();
    }
    public List<RestaurantResponse> fetchAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantResponse> responses = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            responses.add(new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getCity()));
        }
        return responses;
    }
    public RestaurantResponse fetch(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).get();
        return new RestaurantResponse(id, restaurant.getName(), restaurant.getCity());
    }
}
