package de.luisgerlinger.chatserver.service;

import de.luisgerlinger.chatserver.entity.UserBE;
import de.luisgerlinger.chatserver.repository.UserRepository;
import de.luisgerlinger.chatserver.service.dto.UserUiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBA {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String password) {
        UserBE user = new UserBE();
        user.setUsername(username);
        user.setPassword(
                passwordEncoder.encode(password)
        );
        userRepository.save(user);
    }

    public Optional<UserUiDTO> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .map(user -> new UserUiDTO(user.getId(), user.getUsername()));
    }

    public Optional<UserUiDTO> getUserById(UUID id) {
        return userRepository.getUserById(id)
                .map(user -> new UserUiDTO(user.getId(), user.getUsername()));
    }
}
