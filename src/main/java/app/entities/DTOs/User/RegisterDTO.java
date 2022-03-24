package app.entities.DTOs.User;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDTO {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String username;
    private String token;
}
