package com.example.catalog.controllers;

import com.example.catalog.entities.Item;
import com.example.catalog.entities.Money;
import com.example.catalog.entities.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.enums.Currency;
import com.example.catalog.models.requests.RestaurantRequest;
import com.example.catalog.models.responses.RestaurantResponse;
import com.example.catalog.services.RestaurantsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RestaurantsControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private RestaurantsService restaurantsService;
    @BeforeEach
    void setup() {
        reset(restaurantsService);
    }

    @Test
    void testAddingARestaurant_success() throws Exception {
        String name = "restaurantOne";
        City city = City.MUMBAI;
        String request = new ObjectMapper().writeValueAsString(new RestaurantRequest(name, city));
        String expectedResponse = "Created a Restaurant with id: 1";
        when(restaurantsService.create(name, city)).thenReturn(expectedResponse);

        mvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(restaurantsService, times(1)).create(name, city);
    }
    @Test
    void testFetchingARestaurant_success() throws Exception {
        String name = "restaurantOne";
        City city = City.MUMBAI;
        Item itemOne = new Item("itemOne", new Money(10.0, Currency.INR), new Restaurant());
        Item itemTwo = new Item("itemTwo", new Money(10.0, Currency.INR), new Restaurant());
        List<Item> menu = new ArrayList<>(List.of(itemOne, itemTwo));
        RestaurantResponse restaurantResponse = new RestaurantResponse(1L, name, city);
        String expectedResponse = new ObjectMapper().writeValueAsString(restaurantResponse);
        when(restaurantsService.fetch(1L)).thenReturn(restaurantResponse);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(restaurantsService, times(1)).fetch(1L);

    }
    @Test
    void testFetchingAllRestaurants_success() throws Exception {
        String firstName = "restaurantOne";
        String secondName = "restaurantTwo";
        City city = City.MUMBAI;
        Item itemOne = new Item("itemOne", new Money(10.0, Currency.INR), new Restaurant());
        Item itemTwo = new Item("itemTwo", new Money(10.0, Currency.INR), new Restaurant());
        List<Item> menu = new ArrayList<>(List.of(itemOne, itemTwo));
        RestaurantResponse restaurantResponseOne = new RestaurantResponse(1L, firstName, city);
        RestaurantResponse restaurantResponseTwo = new RestaurantResponse(2L, secondName, city);
        List<RestaurantResponse> responses = new ArrayList<>(List.of(restaurantResponseOne, restaurantResponseTwo));
        String expectedResponse = new ObjectMapper().writeValueAsString(responses);
        when(restaurantsService.fetchAll()).thenReturn(responses);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(restaurantsService, times(1)).fetchAll();
    }

}