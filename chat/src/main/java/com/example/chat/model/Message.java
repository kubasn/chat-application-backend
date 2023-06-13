package com.example.chat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String messageId;
    private String content;
    private LocalDateTime creation_date;


    private String userId;

    @ManyToOne
    private ChatRoom chatRoomId;

    @PrePersist
    void prePersist(){
        creation_date = LocalDateTime.now();
    }
}
