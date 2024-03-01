package com.example.catalog.entity;

import java.io.Serializable;

import com.example.catalog.enums.Currency;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Money implements Serializable{
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Money add(Money other) {
        return new Money(this.amount + other.amount, this.currency);
    }
}
