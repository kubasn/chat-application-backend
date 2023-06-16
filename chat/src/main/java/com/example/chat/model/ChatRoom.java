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
import java.util.stream.Collectors;

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


//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})

    @CollectionTable(name = "user_chatroom",
            joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "user_id")
    @JoinColumn(name = "room_id")
    @ElementCollection
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<String> users = new ArrayList<>();

//    @OneToMany(targetEntity=Message.class,cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "room_id", referencedColumnName = "chat_room_id")
//            private List<Message> messages = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "chatRoomId")
@Transient
   private List<Message> messages = new ArrayList<>();



    public ChatRoom(ChatRoomDTO chatRoomDTO) {
        this.roomId = chatRoomDTO.getRoomId();
        this.name = chatRoomDTO.getName();
        this.description = chatRoomDTO.getDescription();
        this.picture = chatRoomDTO.getPicture();
        this.creation_date = chatRoomDTO.getCreation_date();
//        this.messages = chatRoomDTO.getMessages().stream().map(Message::new).collect(Collectors.toList());
    }

    public ChatRoomDTO convertToDto(){

//        List<MessageDTO> messageDtos = new ArrayList<>(messages.stream().map(Message::convertToDto
//        ).toList());

        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setRoomId(this.roomId);
        dto.setDescription(this.description);
        dto.setName(this.name);
//        dto.setMessages(messageDtos);


        return dto;
    }

    @PrePersist
    void prePersist() {
        creation_date = LocalDateTime.now();
    }
}
