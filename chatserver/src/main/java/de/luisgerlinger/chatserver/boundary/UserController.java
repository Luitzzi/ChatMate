package de.luisgerlinger.chatserver.boundary;

import de.luisgerlinger.chatserver.service.UserBA;
import de.luisgerlinger.chatserver.service.dto.UserUiDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserBA userBA;

    @GetMapping("/{username}")
    public ResponseEntity<UUID> getUserByUsername(@PathVariable String username) {
        Optional<UserUiDTO> user = userBA.getUserByUsername(username);
        if (user.isPresent())
            return ResponseEntity.ok(user.get().getId());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public List<UserUiDTO> getAllUsers() {
        return userBA.getAllUsers();
    }

    @PostMapping("/contacts")
    public ResponseEntity<Void> addContact(
            @RequestParam UUID userId,
            @RequestParam UUID contactId
    ) {
        try {
            userBA.addContact(userId, contactId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/contacts/{userId}")
    public Set<UserUiDTO> getContacts(@PathVariable UUID userId) {
        return userBA.getContacts(userId);
    }
}
