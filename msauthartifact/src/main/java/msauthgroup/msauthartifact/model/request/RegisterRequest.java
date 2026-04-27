package msauthgroup.msauthartifact.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email(message = "Email duzgun deyil")
    @NotBlank(message = "Email bosh ola bilmez")
    private String email;

    @NotBlank(message = "Sifri bosh ola bilmez")
    @Size(min = 6, message = "sifre min 6 simvol olmalidir")
    private String password;
}
