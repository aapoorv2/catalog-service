package com.example.catalog.models.responses;

import com.example.catalog.entities.Money;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ItemResponse {
    private Long id;
    private String name;
    private Money price;
}
