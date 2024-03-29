package com.example.catalog.services;

import com.example.catalog.entities.Item;
import com.example.catalog.entities.Money;
import com.example.catalog.entities.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.enums.Currency;
import com.example.catalog.exceptions.ItemAlreadyExistsException;
import com.example.catalog.exceptions.ItemNotFoundException;
import com.example.catalog.exceptions.RestaurantNotFoundException;
import com.example.catalog.models.responses.ItemResponse;
import com.example.catalog.models.responses.RestaurantResponse;
import com.example.catalog.repositories.ItemRepository;
import com.example.catalog.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.example.catalog.constants.TestNamings.*;
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

        itemsService.create(TEST_ITEM_NAME, new Money(10.0, Currency.INR), 1L);

        verify(itemRepository, times(1)).save(any(Item.class));
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testAddingAnItemWithTheSameName_expectException() {
        Item item = Item.builder().name(TEST_ITEM_NAME).build();
        Restaurant restaurant = new Restaurant(1L, TEST_RESTAURANT_NAME, List.of(item), City.DELHI);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        assertThrows(ItemAlreadyExistsException.class, () -> {
            itemsService.create(TEST_ITEM_NAME, new Money(10.0, Currency.INR), 1L);
        });

        verify(itemRepository, times(0)).save(any(Item.class));
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchingAnItem_success() {
        String name = TEST_ITEM_NAME;
        Item item = new Item(name, new Money(10.0, Currency.INR), new Restaurant(1L, TEST_ITEM_NAME, City.MUMBAI));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ItemResponse expectedResponse = new ItemResponse(1L, name, new Money(10.0, Currency.INR));

        ItemResponse response = itemsService.fetch(1L, 1L);

        assertEquals(expectedResponse, response);
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchingAnItem_expectException(){
        assertThrows(ItemNotFoundException.class, () -> {
            itemsService.fetch(1L, 1L);
        });
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchingAllItems_success() {
        Item firstItem = Item.builder().id(1L).price(new Money()).name(TEST_ITEM_NAME).build();
        Item secondItem = Item.builder().id(2L).price(new Money()).name(TEST_ITEM_NAME_TWO).build();
        Restaurant restaurant = new Restaurant(1L, TEST_RESTAURANT_NAME, List.of(firstItem, secondItem), City.DELHI);
        ItemResponse firstResponse = new ItemResponse(firstItem.getId(), firstItem.getName(), firstItem.getPrice());
        ItemResponse secondResponse = new ItemResponse(secondItem.getId(), secondItem.getName(), secondItem.getPrice());
        List<ItemResponse> expectedResponses = List.of(firstResponse, secondResponse);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        List<ItemResponse> responses = itemsService.fetchAll(1L);

        assertEquals(expectedResponses, responses);
        verify(restaurantRepository, times(1)).findById(1L);
    }

}