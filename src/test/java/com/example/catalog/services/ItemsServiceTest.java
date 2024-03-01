package com.example.catalog.services;

import com.example.catalog.entity.Item;
import com.example.catalog.entity.Money;
import com.example.catalog.entity.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.enums.Currency;
import com.example.catalog.models.responses.ItemResponse;
import com.example.catalog.models.responses.RestaurantResponse;
import com.example.catalog.repositories.ItemRepository;
import com.example.catalog.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ItemsServiceTest {
    @Mock
    ItemRepository itemRepository;
    @Mock
    RestaurantRepository restaurantRepository;
    @InjectMocks
    ItemsService itemsService;
    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testAddingAnItem_success() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(new Restaurant()));

        String response = itemsService.addItem("test_name", new Money(10.0, Currency.INR), 1L);

        verify(itemRepository, times(1)).save(any(Item.class));
        assertEquals("Created an Item with id: 1", response);
    }
    @Test
    void testFetchingAnItem_success() {
        String name = "test_name";
        Item item = new Item(name, new Money(10.0, Currency.INR), new Restaurant());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ItemResponse expectedResponse = new ItemResponse(1L, name, new Money(10.0, Currency.INR));

        ItemResponse response = itemsService.fetchItem(1L);

        assertEquals(expectedResponse, response);
    }

}