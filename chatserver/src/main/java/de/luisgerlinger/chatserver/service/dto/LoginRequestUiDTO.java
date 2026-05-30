package de.luisgerlinger.chatserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginRequestUiDTO {
    private final String username;
    private final String password;
}
