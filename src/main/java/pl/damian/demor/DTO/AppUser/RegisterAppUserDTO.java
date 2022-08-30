package pl.damian.demor.DTO.AppUser;

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
    private String email;

    @NotBlank
    @Size(min = 5, max = 50,
            message = "Password must be between 5 and 50 characters")
    private String password;

    @NotBlank(message = "First name cannot be empty")
    private String firstname;

    @NotBlank(message = "Last name cannot be empty")
    private String lastname;
}
