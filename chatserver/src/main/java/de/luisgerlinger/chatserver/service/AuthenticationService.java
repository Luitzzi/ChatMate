package de.luisgerlinger.chatserver.service;

import de.luisgerlinger.chatserver.security.JwtUtil;
import de.luisgerlinger.chatserver.service.dto.LoginReplyUiDTO;
import de.luisgerlinger.chatserver.service.dto.LoginRequestUiDTO;
import de.luisgerlinger.chatserver.service.dto.RegistrationRequestUiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserBA userBA;

    public void register(RegistrationRequestUiDTO registrationRequest) {
        userBA.create(registrationRequest.getUsername(), registrationRequest.getPassword());
    }

    public LoginReplyUiDTO login(LoginRequestUiDTO loginRequest) {
        String username = loginRequest.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        loginRequest.getPassword()
                )
        );
        UUID userId = userBA.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"))
                .getId();
        return LoginReplyUiDTO.builder()
                .authToken(jwtUtil.generateToken(userId))
                .userId(userId)
                .build();
    }
}
