package com.example.catalog.controllers;

import com.example.catalog.entities.Money;
import com.example.catalog.enums.Currency;
import com.example.catalog.exceptions.ItemAlreadyExistsException;
import com.example.catalog.models.requests.ItemRequest;
import com.example.catalog.models.responses.ItemResponse;
import com.example.catalog.services.ItemsService;
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
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemsControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    ItemsService itemsService;
    @BeforeEach
    void setup() {
        reset(itemsService);
    }

    @Test
    void testAddingAnItemToARestaurant_success() throws Exception {
        String name = "itemOne";
        Money price = new Money(10.0, Currency.INR);
        String expectedResponse = "Created an Item with id: 1";
        when(itemsService.create(name, price, 1L)).thenReturn(expectedResponse);
        String request = new ObjectMapper().writeValueAsString(new ItemRequest(name, price));

        mvc.perform(MockMvcRequestBuilders.post("/restaurants/1/items")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(itemsService, times(1)).create(name, price, 1L);
    }

    @Test
    void testAddingAnItemWithTheSameName_expectErrorResponse() throws Exception {
        String name = "existing_name";
        Money price = new Money(10.0, Currency.INR);
        String expectedResponse = "An item with the same name already exists";
        when(itemsService.create(name, price, 1L)).thenThrow(new ItemAlreadyExistsException(expectedResponse));
        String request = new ObjectMapper().writeValueAsString(new ItemRequest(name, price));

        mvc.perform(MockMvcRequestBuilders.post("/restaurants/1/items")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(itemsService, times(1)).create(name, price, 1L);
    }
    @Test
    void testFetchingAnItem_success() throws Exception {
        String name = "itemOne";
        Money price = new Money(10.0, Currency.INR);
        ItemResponse itemResponse = new ItemResponse(1L, name, price);
        String expectedResponse = new ObjectMapper().writeValueAsString(itemResponse);
        when(itemsService.fetch(1L, 1L)).thenReturn(itemResponse);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(itemsService, times(1)).fetch(1L, 1L);
    }

    @Test
    void testFetchingAllItems_success() throws Exception {
        ItemResponse itemResponseOne = new ItemResponse(1L, "itemOne", new Money(10.0, Currency.INR));
        ItemResponse itemResponseTwo = new ItemResponse(2L, "itemTwo", new Money(10.0, Currency.INR));
        List<ItemResponse> responses = new ArrayList<>(List.of(itemResponseOne, itemResponseTwo));
        String expectedResponse = new ObjectMapper().writeValueAsString(responses);
        when(itemsService.fetchAll(1L)).thenReturn(responses);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1/items")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(itemsService, times(1)).fetchAll(1L);
    }

}