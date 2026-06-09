package de.luisgerlinger.chatserver.service.mapper;

import de.luisgerlinger.chatserver.entity.UserBE;
import de.luisgerlinger.chatserver.service.dto.UserUiDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserUiDTO toDTO(UserBE entity) {
        return new UserUiDTO(
                entity.getId(),
                entity.getUsername()
        );
    }
}
