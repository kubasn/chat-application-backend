package com.example.chat.repository;

import com.example.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {

    List<ChatRoom> findByName(String name);

    Optional<ChatRoom> findByRoomId(String id);

    @Modifying
    @Query("update ChatRoom c set c.users = :users where c.roomId = :roomId")
    void updateUsers(@Param("users") List<String> users, @Param("roomId") String roomId);

}
