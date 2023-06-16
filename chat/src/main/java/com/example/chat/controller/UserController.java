package com.example.chat.controller;


import com.example.chat.exception.BadRequestException;
import com.example.chat.exception.NotFoundException;
import com.example.chat.model.User;
import com.example.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        try{
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok().body(users);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id){
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok().body(user);
        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch(BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        }

}
