package com.example.catalog.exceptions;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String s) {
        super(s);
    }
}
