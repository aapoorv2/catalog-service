package com.example.catalog.models.responses;

import com.example.catalog.entity.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemResponse {
    private Long id;
    private String name;
    private Money price;
}
