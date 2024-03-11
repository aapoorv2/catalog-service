package com.example.catalog.controllers;

import com.example.catalog.entities.Item;
import com.example.catalog.entities.Money;
import com.example.catalog.entities.Restaurant;
import com.example.catalog.enums.City;
import com.example.catalog.enums.Currency;
import com.example.catalog.exceptions.RestaurantAlreadyExistsException;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.example.catalog.constants.Error.RESTAURANT_ALREADY_EXISTS;
import static com.example.catalog.constants.TestNamings.*;
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
    @WithMockUser
    void testAddingARestaurant_success() throws Exception {
        String name = TEST_RESTAURANT_NAME;
        City city = City.MUMBAI;
        String request = new ObjectMapper().writeValueAsString(new RestaurantRequest(name, city));
        String expectedResponse = TEST_RESTAURANT_CREATED;
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
    @WithMockUser
    void testAddingARestaurantWithSameName_expectErrorResponse() throws Exception {
        String name = TEST_RESTAURANT_NAME;
        City city = City.MUMBAI;
        String request = new ObjectMapper().writeValueAsString(new RestaurantRequest(name, city));
        String expectedResponse = RESTAURANT_ALREADY_EXISTS;
        when(restaurantsService.create(name, city)).thenThrow(new RestaurantAlreadyExistsException(expectedResponse));

        mvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(restaurantsService, times(1)).create(name, city);
    }
    @Test
    void testFetchingARestaurant_success() throws Exception {
        String name = TEST_RESTAURANT_NAME;
        City city = City.MUMBAI;
        Item itemOne = new Item(TEST_ITEM_NAME, new Money(10.0, Currency.INR), new Restaurant());
        Item itemTwo = new Item(TEST_ITEM_NAME_TWO, new Money(10.0, Currency.INR), new Restaurant());
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
        String firstName = TEST_RESTAURANT_NAME;
        String secondName = TEST_RESTAURANT_NAME_TWO;
        City city = City.MUMBAI;
        Item itemOne = new Item(TEST_ITEM_NAME, new Money(10.0, Currency.INR), new Restaurant());
        Item itemTwo = new Item(TEST_ITEM_NAME_TWO, new Money(10.0, Currency.INR), new Restaurant());
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