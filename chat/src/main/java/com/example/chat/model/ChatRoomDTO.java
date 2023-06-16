package com.example.chat.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
    private String roomId;
    private String name;
    private String description;
    private String picture;
    private LocalDateTime creation_date;
//    private List<MessageDTO> messages;
}