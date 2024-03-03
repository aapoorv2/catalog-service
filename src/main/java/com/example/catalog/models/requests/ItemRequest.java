package com.example.catalog.models.requests;

import com.example.catalog.entities.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemRequest {
    private String name;
    private Money price;
}
