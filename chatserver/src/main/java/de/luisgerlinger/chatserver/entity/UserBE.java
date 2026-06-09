package de.luisgerlinger.chatserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = UserBE.TABLE_NAME)
@Getter
@Setter
public class UserBE {
    public static final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany
    @JoinColumn(name = "contact_holder_id", referencedColumnName = "id")
    private Set<UserBE> contacts = new HashSet<>();
}
