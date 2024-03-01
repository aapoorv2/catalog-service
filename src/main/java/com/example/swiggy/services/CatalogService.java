package com.example.swiggy.services;

import com.example.swiggy.entity.Restaurant;
import com.example.swiggy.enums.Location;
import com.example.swiggy.models.requests.RestaurantRequest;
import com.example.swiggy.models.responses.RestaurantResponse;
import com.example.swiggy.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public List<RestaurantResponse> fetchRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantResponse> responses = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            responses.add(new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getMenu(), restaurant.getLocation()));
        }
        return responses;
    }
    public RestaurantResponse fetchItems(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return new RestaurantResponse(restaurant.get().getId(), restaurant.get().getName(), restaurant.get().getMenu(), restaurant.get().getLocation());
    }

    public String createRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant(restaurantRequest.getName(), restaurantRequest.getMenu(), restaurantRequest.getLocation());
        restaurantRepository.save(restaurant);
        return "Created a Restaurant with id: " + restaurant.getId();
    }
}
