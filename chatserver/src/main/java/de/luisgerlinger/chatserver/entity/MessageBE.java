package de.luisgerlinger.chatserver.entity;

import de.luisgerlinger.grpc.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = MessageBE.TABLE_NAME)
@Getter
@Setter
public class MessageBE {
    public static final String TABLE_NAME = "messages";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatBE chat;

    private MessageType type;

    private UUID senderId;

    private UUID targetId;

    private String message;

    private Instant timestamp;
}
