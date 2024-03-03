package com.example.catalog.models.responses;

import com.example.catalog.enums.City;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RestaurantResponse {
    private Long restaurant_id;
    private String name;
    @Enumerated(EnumType.STRING)
    private City city;

}
