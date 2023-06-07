package com.example.chat.service;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
        System.out.println(userId);
        System.out.println(roomId);
        User user = userRepository.findByUserId(userId).orElse(null);
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElse(null);
        System.out.println(user.getLogin());
        System.out.println(chatRoom.getName());
        user.getChatRooms().add(chatRoom.getRoomId());
//        chatRoom.getUsers().add(user.getUserId());


       userRepository.save(user);
//       chatRoomRepository.save(chatRoom);

    }

    public boolean removeUserFromChatRoom(String userId , String  roomId){
        System.out.println(roomId);
        ChatRoom existingChatRoom = chatRoomRepository.findByRoomId(roomId).orElse(null);
        User existingUser = userRepository.findByUserId(userId).orElse(null);
        System.out.println(existingChatRoom);
        if(existingChatRoom !=null && existingUser !=null){

            List<String> users = existingChatRoom.getUsers();
            System.out.println(users);
            users.remove(userId);
            System.out.println(users);  List<String> userChatRooms = existingUser.getChatRooms();
//            userChatRooms.remove(roomId);
//            System.out.println(userChatRooms);
//            userRepository.updateChatRooms(userChatRooms, userId);
            chatRoomRepository.updateUsers(users, roomId);

//

            return true;

        }
        System.out.println("ello");

        return false;


    }

//    public boolean leaveRoom(int userId)


}
