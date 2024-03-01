package com.example.catalog.models.requests;

import com.example.catalog.enums.City;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestaurantRequest {
    private String name;
    @Enumerated(EnumType.STRING)
    private City city;
}
