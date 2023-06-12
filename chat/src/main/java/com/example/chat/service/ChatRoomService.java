package com.example.chat.service;

import com.example.chat.exception.NotFoundException;
import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Transactional
@Service
public class ChatRoomService {

    private ChatRoomRepository chatRoomRepository;

    private UserRepository userRepository;


    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }


    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom getChatRoomById(String id) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);
        return chatRoomOptional.orElse(null);
    }

    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        return (ChatRoom) chatRoomRepository.save(chatRoom);
    }


    public ChatRoom updateChatRoom(String id, ChatRoom chatRoom) {
        ChatRoom existingChatRoom = (ChatRoom) chatRoomRepository.findById(id).orElse(null);
        if (existingChatRoom != null) {
            chatRoom.setRoomId(existingChatRoom.getRoomId());
            return (ChatRoom) chatRoomRepository.save(chatRoom);
        }
        return null;
    }

    public boolean deleteChatRoom(String roomId) {
        ChatRoom existingChatRoom = (ChatRoom) chatRoomRepository.findByRoomId(roomId).orElse(null);

        if (existingChatRoom != null) {
            existingChatRoom.getUsers().stream()
                    .map(userId -> userRepository.findByUserId(userId).orElse(null))
                    .filter(Objects::nonNull)
                    .forEach(user -> {
                        List<String> chatRooms = user.getChatRooms();
                        chatRooms.remove(roomId);
                    });


            chatRoomRepository.delete(existingChatRoom);
            return true;
        }
        return false;
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

    public void removeUserFromChatRoom(String userId, String roomId) {
        ChatRoom existingChatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(() -> new NotFoundException("Chat room not found"));
        User existingUser = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found"));

        List<String> toOverride = existingUser.getChatRooms().stream().filter(roomInside -> !Objects.equals(existingChatRoom.getRoomId(),roomInside)).toList();
        System.out.println("ello");
        System.out.println(toOverride);
        System.out.println("ello");
        existingUser.setChatRooms(toOverride);



    }



}
