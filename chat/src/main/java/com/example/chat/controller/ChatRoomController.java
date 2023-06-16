package com.example.chat.controller;

import com.example.chat.exception.NotFoundException;
import com.example.chat.model.ChatRoom;
import com.example.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping
    public ResponseEntity getAllChatRooms(){
        try {
            List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
            return ResponseEntity.ok().body(chatRooms);
        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity getChatRoomById(@PathVariable("id") String id) {
        try {
            ChatRoom chatRoom = chatRoomService.getChatRoomById(id);
            return ResponseEntity.ok().body(chatRoom);
        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity createChatRoom(@RequestBody ChatRoom chatRoom){

        try {
            ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoom);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdChatRoom);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }



    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable String roomId){
        try {
            chatRoomService.deleteChatRoom(roomId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateChatRoom(@PathVariable String id, @RequestBody ChatRoom chatRoom){
        try {
            ChatRoom updatedChatRoom = chatRoomService.updateChatRoom(id,chatRoom);
            return ResponseEntity.ok().body(updatedChatRoom);
        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


    @Transactional
    @PutMapping("/{roomId}/users/{userId}")
    public ResponseEntity<String> addUserToChatRoom(@PathVariable String roomId, @PathVariable String userId){
        try {
            chatRoomService.addUserToChatRoom(userId, roomId);
            return ResponseEntity.ok().body("User with ID" + userId + " added to ChatRoom with ID " + roomId);

        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


    @DeleteMapping("/{roomId}/users/{userId}")
    public ResponseEntity<?> removeUserFromChatRoom(@PathVariable String roomId, @PathVariable String userId){
        try {
            chatRoomService.removeUserFromChatRoom(userId,roomId);
            return ResponseEntity.ok().body("User with ID " + userId + " removed from ChatRoom with ID " + roomId);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
