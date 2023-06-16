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

//    @ManyToOne
    private String userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="chat_room_id",nullable = false)
    private String chatRoomId;

    public Message(MessageDTO messageDTO) {
        this.content = messageDTO.getContent();
        this.creation_date = messageDTO.getCreation_date();
        this.userId = messageDTO.getUserId();
        this.chatRoomId = messageDTO.getChatRoomId();
        // Przy założeniu, że User jest już utworzony i dostępny
        // this.user = // Tu powinieneś odnaleźć użytkownika na podstawie messageDTO.getUserId();
//        this.chatRoomId = messageDTO.getChatRoomId();
    };

    public MessageDTO convertToDto() {
        return new MessageDTO(
                this.getMessageId(),
                this.getUserId(),
                this.getCreation_date(),
                this.getContent(),
                this.getChatRoomId()
        );
    }

    @PrePersist
    void prePersist(){
        creation_date = LocalDateTime.now();
    }



}


