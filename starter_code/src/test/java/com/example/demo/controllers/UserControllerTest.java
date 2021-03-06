package com.example.demo.controllers;

import com.example.demo.SareetaApplicationTests;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static com.example.demo.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class UserControllerTest extends SareetaApplicationTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateUser(){
        when(encoder.encode("password")).thenReturn("hashedpassword");
        when(cartRepository.save(Mockito.any())).thenReturn(createCart(createUser()));
        when(userRepository.save(Mockito.any())).thenReturn(createUser());

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("password");
        request.setConfirmPassword("password");
        ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("hashedpassword", user.getPassword());
    }
}
