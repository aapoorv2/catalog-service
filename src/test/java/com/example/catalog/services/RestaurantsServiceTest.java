package com.example.catalog.services;

import com.example.catalog.entities.Item;
import com.example.catalog.entities.Money;
import com.example.catalog.entities.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.enums.Currency;
import com.example.catalog.exceptions.RestaurantAlreadyExistsException;
import com.example.catalog.exceptions.RestaurantNotFoundException;
import com.example.catalog.models.responses.RestaurantResponse;
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

class RestaurantsServiceTest {
    @Mock
    RestaurantRepository restaurantRepository;
    @InjectMocks
    RestaurantsService restaurantsService;
    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testAddingARestaurant_success() {
        String name = "test_name";
        City city = City.MUMBAI;
        Restaurant restaurant = new Restaurant(1L, name, city);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        String response = restaurantsService.create(name, city);

        verify(restaurantRepository, times(1)).findByName(name);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
        assertEquals("Created a Restaurant with id: 1", response);
    }

    @Test
    void testAddingARestaurantWithTheSameName_expectException() {
        String name = "existing_name";
        City city = City.MUMBAI;
        Restaurant existingRestaurant = new Restaurant(1L, name, city);
        when(restaurantRepository.findByName(name)).thenReturn(Optional.of(existingRestaurant));

        assertThrows(RestaurantAlreadyExistsException.class, () -> {
            restaurantsService.create(name, city);
        });

        verify(restaurantRepository, times(1)).findByName(name);
        verify(restaurantRepository, times(0)).save(any(Restaurant.class));
    }
    @Test
    void testFetchingARestaurant_success() {
        String name = "test_name";
        City city = City.DELHI;
        Item itemOne = new Item("itemOne", new Money(10.0, Currency.INR), new Restaurant());
        Item itemTwo = new Item("itemTwo", new Money(10.0, Currency.INR), new Restaurant());
        List<Item> menu = new ArrayList<>(List.of(itemOne, itemTwo));
        Restaurant restaurant = new Restaurant(1L, name, menu, city);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        RestaurantResponse expectedResponse = new RestaurantResponse(1L, name, city);

        RestaurantResponse response = restaurantsService.fetch(1L);

        assertEquals(expectedResponse, response);
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchingARestaurant_expectException() {
        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantsService.fetch(1L);
        });
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchingAllRestaurants_success() {
        Restaurant firstRestaurant = new Restaurant(1L, "test_name", City.DELHI);
        Restaurant secondRestaurant = new Restaurant(2L, "test_name2", City.MUMBAI);
        RestaurantResponse firstResponse = new RestaurantResponse(firstRestaurant.getId(), firstRestaurant.getName(), firstRestaurant.getCity());
        RestaurantResponse secondResponse = new RestaurantResponse(secondRestaurant.getId(), secondRestaurant.getName(), secondRestaurant.getCity());
        List<RestaurantResponse> expectedResponses = List.of(firstResponse, secondResponse);
        when(restaurantRepository.findAll()).thenReturn(List.of(firstRestaurant, secondRestaurant));

        List<RestaurantResponse> responses = restaurantsService.fetchAll();

        assertEquals(expectedResponses, responses);
        verify(restaurantRepository, times(1)).findAll();
    }

}