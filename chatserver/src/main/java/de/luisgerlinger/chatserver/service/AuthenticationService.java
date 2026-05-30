package de.luisgerlinger.chatserver.service;

import de.luisgerlinger.chatserver.entity.UserBE;
import de.luisgerlinger.chatserver.repository.UserRepository;
import de.luisgerlinger.chatserver.security.JwtUtil;
import de.luisgerlinger.chatserver.service.dto.LoginRequestUiDTO;
import de.luisgerlinger.chatserver.service.dto.RegistrationRequestUiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegistrationRequestUiDTO registrationRequest) {
        UserBE user = new UserBE();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(
                passwordEncoder.encode(registrationRequest.getPassword())
        );
        userRepository.save(user);
    }

    public String login(LoginRequestUiDTO loginRequest) {
        String username = loginRequest.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        loginRequest.getPassword()
                )
        );
        UUID userId = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"))
                .getId();
        return jwtUtil.generateToken(userId);
    }
}
