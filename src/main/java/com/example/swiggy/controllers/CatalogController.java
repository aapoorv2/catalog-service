package com.example.swiggy.controllers;

import com.example.swiggy.models.requests.RestaurantRequest;
import com.example.swiggy.models.responses.RestaurantResponse;
import com.example.swiggy.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class CatalogController {
    @Autowired
    CatalogService catalogService;

    @PostMapping("/")
    ResponseEntity<String> addRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.createRestaurant(restaurantRequest))
    }
    @GetMapping("/")
    ResponseEntity<List<RestaurantResponse>> fetchRestaurants() {
        return ResponseEntity.ok().body(catalogService.fetchRestaurants());
    }
    @GetMapping("/{id}")
    ResponseEntity<RestaurantResponse> fetchItems(@PathVariable Long id) {
        return ResponseEntity.ok().body(catalogService.fetchItems(id));
    }
}
