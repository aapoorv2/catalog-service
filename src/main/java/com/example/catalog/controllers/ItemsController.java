package com.example.catalog.controllers;

import com.example.catalog.exceptions.ItemNotFoundException;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(itemsService.create(itemRequest.getName(), itemRequest.getPrice(), restaurantId));
    }
    @GetMapping("/{id}")
    ResponseEntity<?> fetchItem(@PathVariable Long restaurantId, @PathVariable Long id) {
        try {
            ItemResponse response = itemsService.fetch(restaurantId, id);
            return ResponseEntity.ok().body(response);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("")
    ResponseEntity<List<ItemResponse>> fetchItems(@PathVariable Long restaurantId) {
        return ResponseEntity.ok().body(itemsService.fetchAll(restaurantId));
    }
}
