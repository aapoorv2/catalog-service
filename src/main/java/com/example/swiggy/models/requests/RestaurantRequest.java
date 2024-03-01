package com.example.swiggy.models.requests;

import com.example.swiggy.entity.Item;
import com.example.swiggy.enums.Location;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RestaurantRequest {
    private String name;
    private List<Item> menu;
    @Enumerated(EnumType.STRING)
    private Location location;
}
