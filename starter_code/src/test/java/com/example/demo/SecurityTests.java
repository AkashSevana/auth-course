package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testgetUserWithoutToken() throws Exception {
        String username = "Akash";
        String password = "password";
        String body = "{\"username\":\"" + username + "\", \"password\":\" " + password + "\"}";
        mvc.perform(MockMvcRequestBuilders.post("/api/user/Akash")
                .content(body))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void testGetUserWithToken() throws Exception {
        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZGl0eWEiLCJleHAiOjE2MTYzMzI5NTR9.VKcbN54W0uRascNA64DCWYlg_tsmxxda_8x4rbn2qIBbjSEasPbv2jAYbydtLRGbrQk3sjy5VPBGfS9u_-us7Q";
        mvc.perform(MockMvcRequestBuilders.get("/api/user/Aditya").header("Authorization", token)).andExpect(status().isNotFound());
    }

}
