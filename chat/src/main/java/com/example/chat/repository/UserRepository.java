package com.example.chat.repository;

import com.example.chat.model.ChatRoom;
import com.example.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


     Optional<User> findByUserId(String id);

    List<ChatRoom> findByLogin(String name);


    @Modifying
    @Query("update User u set u.chatRooms = :chatRooms where u.id = :userId")
    void updateChatRooms(@Param("chatRooms") List<String> chatRooms, @Param("userId") String userId);




}


