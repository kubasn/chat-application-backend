package com.example.chat.repository;


import com.example.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message,String> {


    Optional<Message> findByMessageId(String messageId);

    List<Message> findByChatRoomId(String roomId);

}
