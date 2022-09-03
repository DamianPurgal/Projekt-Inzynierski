package pl.damian.demor.DTO.AppUser;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditAppUserDTO {

    @NotBlank
    @Size(min = 5, max = 50,
            message = "Old password must be between 5 and 50 characters")
    private String oldPassword;

    @NotBlank
    @Size(min = 5, max = 50,
            message = "New password must be between 5 and 50 characters")
    private String newPassword;

    @NotBlank(message = "First name cannot be empty")
    private String firstname;

    @NotBlank(message = "Last name cannot be empty")
    private String lastname;
}
