package de.luisgerlinger.chatserver.boundary;

import de.luisgerlinger.chatserver.service.AuthenticationService;
import de.luisgerlinger.chatserver.service.dto.LoginReplyUiDTO;
import de.luisgerlinger.chatserver.service.dto.LoginRequestUiDTO;
import de.luisgerlinger.chatserver.service.dto.RegistrationRequestUiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequestUiDTO registrationRequest) {
        authenticationService.register(registrationRequest);
    }

    @PostMapping("login")
    public LoginReplyUiDTO login(@RequestBody LoginRequestUiDTO loginRequest) {
        return authenticationService.login(loginRequest);
    }
}
