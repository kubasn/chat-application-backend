package com.example.chat.service;

import com.example.chat.exception.BadRequestException;
import com.example.chat.exception.NotFoundException;
import com.example.chat.model.ChatRoom;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.repository.MessageRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Transactional
@Service
public class ChatRoomService {

    private MessageRepository messageRepository;
    private ChatRoomRepository chatRoomRepository;

    private UserRepository userRepository;


    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    public List<ChatRoom> getAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();


        if(chatRooms.isEmpty()){
            throw new NotFoundException("Chat rooms not found");
        } else return chatRooms;


    }

    public ChatRoom getChatRoomById(String id) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(id).orElseThrow(
                () -> new NotFoundException("Chat room not found with id" + id)
        );
        List<Message> messages = messageRepository.findByChatRoomId(chatRoom.getRoomId());
        chatRoom.setMessages(messages);
        return chatRoom;
    }

    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        try {
            return (ChatRoom) chatRoomRepository.save(chatRoom);
        } catch(DataIntegrityViolationException e) {
            throw new BadRequestException("Invalid chat room data",e);
        }
        }


    public ChatRoom updateChatRoom(String id, ChatRoom chatRoom) {
        ChatRoom existingChatRoom = (ChatRoom) chatRoomRepository.findById(id).orElseThrow(()-> new NotFoundException("Chat room not found"));

        try {
            chatRoom.setRoomId(existingChatRoom.getRoomId());
            return chatRoomRepository.save(chatRoom);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Could not update chat room",e);
        }
    }

    public void deleteChatRoom(String roomId) {
        ChatRoom existingChatRoom = (ChatRoom) chatRoomRepository.findByRoomId(roomId).orElseThrow(()-> new NotFoundException("Chat room not found"));

        List<Message> messages = messageRepository.findByChatRoomId(roomId);
        for (Message message : messages) {
            messageRepository.delete(message);
        }

        try {
                existingChatRoom.getUsers().stream()
                        .map(userId -> userRepository.findByUserId(userId).orElse(null))
                        .filter(Objects::nonNull)
                        .forEach(user -> {
                            List<String> chatRooms = user.getChatRooms();
                            chatRooms.remove(roomId);
                        });


                chatRoomRepository.delete(existingChatRoom);
        } catch(DataAccessException e) {
                throw new BadRequestException("Could not delete chat room",e);
            }
        }



    public void addUserToChatRoom(String userId, String roomId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found"));
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new NotFoundException("Chat room not found"));
        if (!user.getChatRooms().contains(chatRoom.getRoomId())) {
            user.getChatRooms().add(chatRoom.getRoomId());
            userRepository.save(user);
        } else {
            throw new IllegalStateException("User is already in the chat room");
        }

    }

    public void removeUserFromChatRoom(String userId, String roomId) throws Exception {
        ChatRoom existingChatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new NotFoundException("Chat room not found"));
        User existingUser = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found"));

        List<String> toOverride = existingUser.getChatRooms().stream().filter(roomInside -> !Objects.equals(existingChatRoom.getRoomId(),roomInside)).toList();

            if(toOverride.size() == existingUser.getChatRooms().size()) {
                throw new Exception("User is not part of the chat room");
         }

        existingUser.setChatRooms(toOverride);



    }



}
