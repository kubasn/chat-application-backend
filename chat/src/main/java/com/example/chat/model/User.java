package com.example.chat.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String login;
    private String password;
    private String avatarID;
    private String role;


    @CollectionTable(name = "user_chatroom",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "room_id")
    @JoinColumn(name = "user_id")
    @ElementCollection
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<String> chatRooms = new ArrayList<>();




}
