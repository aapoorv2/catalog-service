package com.example.swiggy.entity;

import com.example.swiggy.enums.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void testAddingTwoPriceObjectsSuccess() {
        Money moneyOne = new Money(10.0, Currency.INR);
        Money moneyTwo = new Money(20.0, Currency.INR);

        Money totalMoney = moneyOne.add(moneyTwo);

        assertEquals(new Money(30.0, Currency.INR), totalMoney);
    }

}