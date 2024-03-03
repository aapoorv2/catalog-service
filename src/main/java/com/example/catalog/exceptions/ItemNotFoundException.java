package com.example.catalog.exceptions;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String s) {
        super(s);
    }
}
