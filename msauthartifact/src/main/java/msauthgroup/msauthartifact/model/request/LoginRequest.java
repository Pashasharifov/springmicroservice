package msauthgroup.msauthartifact.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "Email duzgun deyil")
    @NotBlank(message = "email bosh ola bilmez")
    private String email;

    @NotBlank(message = "Sifri bosh ola bilmez")
    private String password;
}
