package com.example.swiggy.repositories;

import com.example.swiggy.entity.Restaurant;
import com.example.swiggy.enums.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByLocation(Location location);
}
