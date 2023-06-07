package com.example.chat.service;


import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<User> userOptional = userRepository.findByUserId(id);
        return userOptional.orElse(null);
    }

    public User createUser(User user){
        return (User) userRepository.save(user);
    }


}
