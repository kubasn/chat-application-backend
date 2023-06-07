package com.example.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chat_rooms")
@Data
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roomId;
    private String name;
    private String description;
    private String picture;
    private LocalDateTime creation_date;


    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER)
    private List<Message> messages;

    @CollectionTable(name = "user_chatroom",
            joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "user_id")
    @ElementCollection
    private List<String> users = new ArrayList<>();

    @PrePersist
    void prePersist() {
        creation_date = LocalDateTime.now();
    }
}
