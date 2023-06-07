package com.example.chat.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name= "users")
@Data
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private String userId;

    private String login;
    private String password;
    private String avatarID;
    private String role;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name="user_chatroom",
//            joinColumns = @JoinColumn(name="user_id"),
//            inverseJoinColumns = @JoinColumn(name="room_id")
//
//    )

    @CollectionTable(name = "user_chatroom",
            joinColumns = @JoinColumn(name="user_id"))
            @Column(name="room_id")
    @ElementCollection private List<String> chatRooms = new ArrayList<>();



    @OneToMany(mappedBy = "user")
    List<Message> messages;


}
