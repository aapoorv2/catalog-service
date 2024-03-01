package com.example.swiggy.models.responses;

import com.example.swiggy.entity.Item;
import com.example.swiggy.enums.Location;
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
    private Location location;

}
