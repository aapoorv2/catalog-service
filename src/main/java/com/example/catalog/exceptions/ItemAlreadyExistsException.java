package com.example.catalog.exceptions;

public class ItemAlreadyExistsException extends RuntimeException {
    public ItemAlreadyExistsException(String s) {
        super(s);
    }
}
