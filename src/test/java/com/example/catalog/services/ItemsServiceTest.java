package com.example.catalog.services;

import com.example.catalog.entities.Item;
import com.example.catalog.entities.Money;
import com.example.catalog.entities.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.enums.Currency;
import com.example.catalog.models.responses.ItemResponse;
import com.example.catalog.repositories.ItemRepository;
import com.example.catalog.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

        itemsService.addItem("test_name", new Money(10.0, Currency.INR), 1L);

        verify(itemRepository, times(1)).save(any(Item.class));
    }
    @Test
    void testFetchingAnItem_success() {
        String name = "test_name";
        Item item = new Item(name, new Money(10.0, Currency.INR), new Restaurant(1L, "test_name", City.MUMBAI));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ItemResponse expectedResponse = new ItemResponse(1L, name, new Money(10.0, Currency.INR));

        ItemResponse response = itemsService.fetchItem(1L, 1L);

        assertEquals(expectedResponse, response);
    }

}