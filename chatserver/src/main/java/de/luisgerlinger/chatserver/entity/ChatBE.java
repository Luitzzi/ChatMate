package de.luisgerlinger.chatserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = ChatBE.TABLE_NAME)
@Getter
@Setter
public class ChatBE {
    public static final String TABLE_NAME = "chats";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(name = "chat_user_bridge",
            joinColumns = { @JoinColumn(name = "chat_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    private Set<UserBE> participants = new HashSet<>();

    @OneToMany(mappedBy = "chat")
    private List<MessageBE> messages = new ArrayList<>();
}
