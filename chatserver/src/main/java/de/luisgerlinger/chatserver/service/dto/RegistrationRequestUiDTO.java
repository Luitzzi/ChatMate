package de.luisgerlinger.chatserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationRequestUiDTO {
    private final String username;
    private final String password;
}
