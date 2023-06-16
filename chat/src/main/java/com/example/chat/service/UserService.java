package com.example.chat.service;


import com.example.chat.exception.BadRequestException;
import com.example.chat.exception.NotFoundException;
import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserById(String id){
        return userRepository.findByUserId(id)
                .orElseThrow(()-> new NotFoundException("User not found"));
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Invalid user data",e);
        }
    }

    public List<User> getAllUsers () {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new NotFoundException("Users not found");
        } else return userRepository.findAll();
    }

}
