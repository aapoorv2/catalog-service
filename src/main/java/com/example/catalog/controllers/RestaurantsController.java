package com.example.catalog.controllers;

import com.example.catalog.exceptions.RestaurantAlreadyExistsException;
import com.example.catalog.exceptions.RestaurantNotFoundException;
import com.example.catalog.models.requests.RestaurantRequest;
import com.example.catalog.models.responses.RestaurantResponse;
import com.example.catalog.services.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {
    @Autowired
    RestaurantsService restaurantsService;

    @PostMapping("")
    ResponseEntity<String> addRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantsService.create(restaurantRequest.getName(), restaurantRequest.getCity()));
        } catch (RestaurantAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    ResponseEntity<List<RestaurantResponse>> fetchAllRestaurants() {
        return ResponseEntity.ok().body(restaurantsService.fetchAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<RestaurantResponse> fetchRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok().body(restaurantsService.fetch(id));
    }

}
