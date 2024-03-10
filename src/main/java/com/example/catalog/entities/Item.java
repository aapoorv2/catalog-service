package com.example.catalog.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Money price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    public Item(String name, Money price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }
}
