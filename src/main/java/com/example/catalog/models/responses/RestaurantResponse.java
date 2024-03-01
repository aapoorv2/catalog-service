package com.example.catalog.models.responses;

import com.example.catalog.entity.Item;
import com.example.catalog.enums.City;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RestaurantResponse {
    private Long restaurant_id;
    private String name;
    private List<Item> menu;
    @Enumerated(EnumType.STRING)
    private City city;

}
