package com.example.demo.controllers;

import com.example.demo.SareetaApplicationTests;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static com.example.demo.TestUtils.*;

public class CartControllerTest extends SareetaApplicationTests {

    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setup(){
        when(userRepository.findByUsername("test")).thenReturn(createUser());
        when(itemRepository.findById(any())).thenReturn(Optional.of(createItem(0)));
    }

    @Test
    public void testAddToCart(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(3);
        request.setItemId(1);
        request.setUsername("test");

        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart actualCart = response.getBody();

        Cart generatedCart = createCart(createUser());

        assertEquals("test", actualCart.getUser().getUsername());
        assertEquals(generatedCart.getItems().size() + request.getQuantity(), actualCart.getItems().size());

        verify(cartRepository, times(1)).save(actualCart);

    }

    @Test
    public void testRemoveFromCart(){

        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(1);
        request.setItemId(1);
        request.setUsername("test");

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart actualCart = response.getBody();

        Cart generatedCart = createCart(createUser());

        assertEquals("test", actualCart.getUser().getUsername());
        assertEquals(generatedCart.getItems().size() - request.getQuantity(), actualCart.getItems().size());

        verify(cartRepository, times(1)).save(actualCart);

    }

    @Test
    public void testInvalidUsername(){

        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(1);
        request.setItemId(1);
        request.setUsername("testInvalidUser");

        ResponseEntity<Cart> removeResponse = cartController.removeFromcart(request);
        assertNotNull(removeResponse);
        assertEquals(404, removeResponse.getStatusCodeValue());

        ResponseEntity<Cart> addResponse = cartController.addTocart(request);
        assertNotNull(addResponse);
        assertEquals(404, addResponse.getStatusCodeValue());

        verify(userRepository, times(2)).findByUsername("testInvalidUser");

    }
}
