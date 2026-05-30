package de.luisgerlinger.chatserver.boundary;

import de.luisgerlinger.chatserver.service.AuthenticationService;
import de.luisgerlinger.chatserver.service.dto.LoginRequestUiDTO;
import de.luisgerlinger.chatserver.service.dto.RegistrationRequestUiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(@RequestBody LoginRequestUiDTO loginRequest) {
        return authenticationService.login(loginRequest);
    }
}
