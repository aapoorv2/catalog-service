package com.example.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Money price;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    public Item(String name, Money price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }
}
