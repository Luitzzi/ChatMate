package de.luisgerlinger.chatserver.service;

import de.luisgerlinger.chatserver.entity.UserBE;
import de.luisgerlinger.chatserver.repository.UserRepository;
import de.luisgerlinger.chatserver.service.dto.UserUiDTO;
import de.luisgerlinger.chatserver.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBA {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
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
                .map(userMapper::toDTO);
    }

    public Optional<UserUiDTO> getUserById(UUID id) {
        return userRepository.getUserById(id)
                .map(userMapper::toDTO);
    }

    public List<UserUiDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public void addContact(UUID userId, UUID contactId) throws EntityNotFoundException{
        UserBE user = userRepository.getUserById(userId)
                .orElseThrow(EntityNotFoundException::new);
        UserBE contact = userRepository.getUserById(contactId)
                .orElseThrow(EntityNotFoundException::new);
        user.getContacts().add(contact);
        userRepository.save(user);
    }

    public Set<UserUiDTO> getContacts(UUID userId) {
        Set<UserBE> constacts = userRepository.getUserById(userId)
                .map(UserBE::getContacts)
                .orElseThrow(EntityNotFoundException::new);
        return constacts.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
