package com.example.demo.controllers;

import com.example.demo.SareetaApplicationTests;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.example.demo.TestUtils.*;

public class ItemControllerTest extends SareetaApplicationTests {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;
    @Before
    public void setup(){
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem(0)));
        when(itemRepository.findAll()).thenReturn(createItems());
        when(itemRepository.findByName("test")).thenReturn(Arrays.asList(createItem(0), createItem(1)));
    }

    @Test
    public void testGetItems(){
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertEquals(createItems(), items);
        verify(itemRepository , times(1)).findAll();
    }

    @Test
    public void testGetItemById(){
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item item = response.getBody();
        assertEquals(createItem(0), item);
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetItemByIdInvalid(){
        ResponseEntity<Item> response = itemController.getItemById(23L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(itemRepository, times(1)).findById(23L);
    }

    @Test
    public void testGetItemByName(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = Arrays.asList(createItem(0), createItem(1));
        assertEquals(createItems(), items);
        verify(itemRepository , times(1)).findByName("test");
    }

    @Test
    public void testGetItemByNameInvalid(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("test name");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(itemRepository , times(1)).findByName("test name");
    }
}
