package com.example.catalog.controllers;

import com.example.catalog.models.requests.ItemRequest;
import com.example.catalog.models.responses.ItemResponse;
import com.example.catalog.services.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/items")
public class ItemsController {
    @Autowired
    ItemsService itemsService;
    @PostMapping("")
    ResponseEntity<String> addItem(@PathVariable Long restaurantId, @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemsService.addItem(itemRequest.getName(), itemRequest.getPrice(), restaurantId));
    }
    @GetMapping("/{id}")
    ResponseEntity<ItemResponse> fetchItem(@PathVariable Long id) {
        return ResponseEntity.ok().body(itemsService.fetchItem(id));
    }
    @GetMapping("")
    ResponseEntity<List<ItemResponse>> fetchItems(@PathVariable Long restaurantId) {
        return ResponseEntity.ok().body(itemsService.fetchAllItems(restaurantId));
    }
}