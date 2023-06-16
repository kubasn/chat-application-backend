package com.example.chat.service;


import com.example.chat.exception.BadRequestException;
import com.example.chat.exception.NotFoundException;
import com.example.chat.model.Message;
import com.example.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  MessageService {
@Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public Message sentMessage(Message message) {
        try {
            return messageRepository.save(message);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Invalid message data",e);
        }

    }

    public Message deleteMessage(String messageId) {

        try {
            Message existingMessage = messageRepository.findByMessageId(messageId).orElseThrow(() -> new NotFoundException("Message not found"));

            existingMessage.setContent("Usunięto wiadomość");
            return messageRepository.save(existingMessage);
        } catch (DataAccessException e) {
            throw new BadRequestException("Could not delete message",e);
        }
    }

}
