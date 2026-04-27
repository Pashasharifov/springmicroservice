package msauthgroup.msauthartifact.service.abstraction;

import msauthgroup.msauthartifact.model.request.LoginRequest;
import msauthgroup.msauthartifact.model.request.RegisterRequest;
import msauthgroup.msauthartifact.model.response.TokenResponse;

public interface AuthService {
    TokenResponse register(RegisterRequest request);
    TokenResponse login(LoginRequest request);
}
