package com.example.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String messageId;
    private String content;
    private LocalDateTime creation_date;
    private String userId;
    private String chatRoomId;
}