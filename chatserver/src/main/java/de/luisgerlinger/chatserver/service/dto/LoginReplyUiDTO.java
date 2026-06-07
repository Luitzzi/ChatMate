package de.luisgerlinger.chatserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LoginReplyUiDTO {
    private String authToken;
    private UUID userId;
}
