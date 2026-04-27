package msauthgroup.msauthartifact.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import msauthgroup.msauthartifact.model.request.LoginRequest;
import msauthgroup.msauthartifact.model.request.RegisterRequest;
import msauthgroup.msauthartifact.model.response.TokenResponse;
import msauthgroup.msauthartifact.service.abstraction.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
