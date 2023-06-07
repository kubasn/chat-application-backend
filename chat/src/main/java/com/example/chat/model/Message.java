package com.example.chat.model;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;

@Entity
@Table(name="messages")
@NoArgsConstructor

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    private String content;
    private LocalDateTime creation_date;

    @ManyToOne
    @JoinColumn(name="user_id", nullable= false)
    private User user;

    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)
    private ChatRoom chatRoom;

    @PrePersist
    void prePersist(){
        creation_date = LocalDateTime.now();
    }


}
