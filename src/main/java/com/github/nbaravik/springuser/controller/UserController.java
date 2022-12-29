package com.github.nbaravik.springuser.controller;

import com.github.nbaravik.springuser.dto.StatusDTO;
import com.github.nbaravik.springuser.dto.UserDTO;
import com.github.nbaravik.springuser.dto.UserIdDTO;
import com.github.nbaravik.springuser.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        ResponseEntity response = ResponseEntity.ok(service.getUserById(id));
        LOGGER.info("{}: GET /users/{}", response.getStatusCode(), id);
        return response;
    }

    @PostMapping("/users")
    public ResponseEntity<UserIdDTO> createUser(@RequestBody UserDTO userDto) {
        ResponseEntity response = new ResponseEntity(service.createUser(userDto), HttpStatus.CREATED);
        LOGGER.info("{}: POST /users/{}", response.getStatusCode(), userDto);
        return response;
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<StatusDTO> updateUserStatus(@PathVariable String id, @RequestParam("status") String status) {
        ResponseEntity response = ResponseEntity.ok(service.modifyStatus(id, status));
        LOGGER.info("{}: PATCH /users/{}?status={}", response.getStatusCode(), id, status);
        return response;
    }
}
