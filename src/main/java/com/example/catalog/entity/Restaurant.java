package com.example.catalog.entity;

import com.example.catalog.enums.City;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Item> menu = new ArrayList<>();
    private City city;

    public Restaurant(String name, City city) {
        this.id = 1L;
        this.name = name;
        this.city = city;
    }
    public void addItem(Item item) {
        menu.add(item);
    }
}
