package com.example.chat.controller;

import com.example.chat.model.ChatRoom;
import com.example.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity getChatRoomById(@PathVariable("id") String id) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(id);
        if (chatRoom != null) {
            return ResponseEntity.ok(chatRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity createChatRoom(@RequestBody ChatRoom chatRoom){
        ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChatRoom);
    }



    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable String roomId){


        boolean deleteChatRoom = chatRoomService.deleteChatRoom(roomId);
        if(deleteChatRoom) {

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ChatRoom> updateChatRoom(@PathVariable String id, @RequestBody ChatRoom chatRoom){
        ChatRoom updatedChatRoom = chatRoomService.updateChatRoom(id,chatRoom);
        if(updatedChatRoom != null){
            return ResponseEntity.ok(updatedChatRoom);
        } else {
            return  ResponseEntity.notFound().build();
        }
    }


    @Transactional
    @PutMapping("/{roomId}/users/{userId}")
    public ResponseEntity addUserToChatRoom(@PathVariable String roomId, @PathVariable String userId){
        chatRoomService.addUserToChatRoom(userId,roomId);
        return ResponseEntity.ok().body("User with ID" + userId + " added to ChatRoom with ID " + roomId);
    }


    @DeleteMapping("/{roomId}/users/{userId}")
    public ResponseEntity removeUserFromChatRoom(@PathVariable String roomId, @PathVariable String userId){
        chatRoomService.removeUserFromChatRoom(userId,roomId);
        return ResponseEntity.ok().body("User with ID" + userId + " removed from ChatRoom with ID " + roomId);
    }

}
