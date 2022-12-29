package com.github.nbaravik.springuser.controller;

import com.github.nbaravik.springuser.dto.UserDTO;
import com.github.nbaravik.springuser.exception.UserNotFoundException;
import com.github.nbaravik.springuser.model.StatusMode;
import com.github.nbaravik.springuser.model.User;
import com.github.nbaravik.springuser.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void getUserById_JsonUserDTOReturned() throws Exception {

        String id = "63aada019c5ac54dfcbd88e1";
        User user = User.builder().
                id(id).
                name("Alexander").
                email("alex@gmail.com").
                uri("https://www.istockphoto.com/photos/alex").
                status(StatusMode.ONLINE).build();

        UserDTO userDTO = UserDTO.builder().
                name("Alexander").
                email("alex@gmail.com").
                uri("https://www.istockphoto.com/photos/alex").
                status("ONLINE").build();

        Mockito.when(service.getUserById(Mockito.any())).thenReturn(userDTO);

        mockMvc.perform(
                get("/users/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.status").value(userDTO.getStatus()));
    }

    @Test
    public void getUserById_UserNotFoundExceptionReturned() throws Exception {

       Mockito.when(service.getUserById("123")).thenThrow(UserNotFoundException.class);

        mockMvc.perform(
                        get("/users/123"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(UserNotFoundException.class));
    }
}
