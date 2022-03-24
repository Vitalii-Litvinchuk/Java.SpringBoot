package app.entities.DTOs.User;

import lombok.Data;

@Data
public class ShortUser {
    private Long id;
    private String email;
    private String token;
}
