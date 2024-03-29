package com.example.catalog.repositories;

import com.example.catalog.enums.City;
import com.example.catalog.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCity(City city);
    Optional<Restaurant> findByName(String name);
}
