package pl.damian.demor.DTO.AppUser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAppUserDTO {

    @Email(message = "Email should be valid")
    @Schema(description="Username/email", example = "DamianPurgal5@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 5, max = 50,
            message = "Password must be between 5 and 50 characters")
    @Schema(description="Password", example = "haslo")
    private String password;

    @NotBlank(message = "First name cannot be empty")
    @Schema(description="Firstname", example = "Damian")
    private String firstname;

    @NotBlank(message = "Last name cannot be empty")
    @Schema(description="Lastname", example = "Purgal")
    private String lastname;
}
