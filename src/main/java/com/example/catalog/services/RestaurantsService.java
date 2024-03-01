package com.example.catalog.services;

import com.example.catalog.entity.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.repositories.ItemRepository;
import com.example.catalog.repositories.RestaurantRepository;
import com.example.catalog.models.responses.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public String addRestaurant(String name, City city) {
        Restaurant restaurant = new Restaurant(name, city);
        restaurantRepository.save(restaurant);
        return "Created a Restaurant with id: " + restaurant.getId();
    }
    public List<RestaurantResponse> fetchAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantResponse> responses = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            responses.add(new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getMenu(), restaurant.getCity()));
        }
        return responses;
    }
    public RestaurantResponse fetchRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).get();
        return new RestaurantResponse(id, restaurant.getName(), restaurant.getMenu(), restaurant.getCity());
    }
}
