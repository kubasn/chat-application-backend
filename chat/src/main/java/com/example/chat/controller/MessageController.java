package com.example.chat.controller;

import com.example.chat.model.Message;
import com.example.chat.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity sendMessage(@RequestBody Message message){
        Message sentMessage = messageService.sentMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }
}
