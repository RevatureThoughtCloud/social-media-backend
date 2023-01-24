package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Option;
import com.revature.dtos.LoginRequest;
import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.NotificationService;
import com.revature.services.PasswordTokenService;

import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService service;

    @MockBean
    private PasswordTokenService passwordService;
    private User recipient;

    private MockHttpSession session;

    @BeforeEach
    void createUserAndNote() {

        recipient = null;
        session = null;

        recipient = new User();

        recipient.setUserName("bob");
        recipient.setEmail("bob@test.com");
        recipient.setPassword("password");
        recipient.setFirstName("bob");
        recipient.setLastName("bob");
        session = new MockHttpSession();
        session.setAttribute("user", recipient);

    }

    @Test
    void testLoginSuccess() throws Exception {

        LoginRequest loginRequest = new LoginRequest(recipient.getEmail(), recipient.getPassword());
        when(service.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword()))
                .thenReturn(Optional.of(recipient));

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("email","bob@test.com");
        body.put("password","password");



        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writer().writeValueAsString(body)).session(session))
                .andExpect(status().isOk()).andExpect((jsonPath("$.userName").value("bob")));

        // verify(service, times(1)).findByCredentials(recipient.getUserName(),
        // recipient.getPassword());

    }

    @Test
    void testLoginFailed() throws Exception {

        LoginRequest loginRequest = new LoginRequest(recipient.getEmail(), recipient.getPassword());
        when(service.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword()))
                .thenReturn(Optional.empty());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("email","bob@test.com");
        body.put("password","password");
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writer().writeValueAsString(body)).session(session))
                .andExpect(status().isBadRequest()).andExpect((jsonPath("$").doesNotExist()));

        // verify(service, times(1)).findByCredentials(loginRequest.getEmail(),
        // loginRequest.getPassword());

    }

    @Test
    void testLogout() throws Exception {

        mockMvc.perform(post("/auth/logout")
                .session(session))
                .andExpect(status().isOk()).andExpect((jsonPath("$").doesNotExist()));
        assertEquals(session.getAttribute("user"), null);
        // verify(service, times(1)).findByCredentials(loginRequest.getEmail(),
        // loginRequest.getPassword());

    }

    @Test
    void testRegister() throws Exception {

        LoginRequest loginRequest = new LoginRequest(recipient.getEmail(), recipient.getPassword());
        when(service.register(recipient)).thenReturn(recipient);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("email","bob@test.com");
        body.put("password","password");
        body.put("userName","bob");
        body.put("firstName","bob");
        body.put("lastName","bob");
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writer().writeValueAsString(body)).session(session))
                .andExpect(status().isCreated()).andExpect((jsonPath("$").exists()));

        // verify(service, times(1)).findByCredentials(loginRequest.getEmail(),
        // loginRequest.getPassword());

    }



}
