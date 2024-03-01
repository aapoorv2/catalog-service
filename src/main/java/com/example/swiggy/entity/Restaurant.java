package com.example.swiggy.entity;

import com.example.swiggy.enums.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Item> menu = new ArrayList<>();
    private Location location;

    public Restaurant(String name, List<Item> menu, Location location) {
        this.name = name;
        this.menu = menu;
        this.location = location;
    }
}
