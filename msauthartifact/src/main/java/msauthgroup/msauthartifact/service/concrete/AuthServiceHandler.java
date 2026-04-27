package msauthgroup.msauthartifact.service.concrete;

import lombok.RequiredArgsConstructor;
import msauthgroup.msauthartifact.dao.entity.UserEntity;
import msauthgroup.msauthartifact.dao.repository.UserRepository;
import msauthgroup.msauthartifact.model.request.LoginRequest;
import msauthgroup.msauthartifact.model.request.RegisterRequest;
import msauthgroup.msauthartifact.model.response.TokenResponse;
import msauthgroup.msauthartifact.security.JwtService;
import msauthgroup.msauthartifact.service.abstraction.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Bu email artiq movcuddur");
        }
        var user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user.getEmail(), user.getRole());
        return TokenResponse.builder().token(token).build();
    }

    @Override
    public TokenResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User tapilmadi"));
        var token = jwtService.generateToken(user.getEmail(), user.getRole());
        return TokenResponse.builder().token(token).build();
    }
}
