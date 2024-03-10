package com.example.catalog.exceptions;

public class RestaurantAlreadyExistsException extends RuntimeException {
    public RestaurantAlreadyExistsException(String s) {
        super(s);
    }
}
