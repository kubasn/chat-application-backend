package com.example.chat.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chat_rooms")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roomId")
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roomId;
    private String name;
    private String description;
    private String picture;
    private LocalDateTime creation_date;




    @CollectionTable(name = "user_chatroom",
            joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "user_id")
    @JoinColumn(name = "room_id")
    @ElementCollection
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    private List<String> users = new ArrayList<>();


    @OneToMany(mappedBy = "chatRoomId")
    private List<Message> messages = new ArrayList<>();

    @PrePersist
    void prePersist() {
        creation_date = LocalDateTime.now();
    }
}
