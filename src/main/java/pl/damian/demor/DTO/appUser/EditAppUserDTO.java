package pl.damian.demor.DTO.appUser;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description="Password", example = "haslo")
    private String oldPassword;

    @NotBlank
    @Size(min = 5, max = 50,
            message = "New password must be between 5 and 50 characters")
    @Schema(description="Password", example = "hasloEdited")
    private String newPassword;

    @NotBlank(message = "First name cannot be empty")
    @Schema(description="Firstname", example = "DamianEdited")
    private String firstname;

    @NotBlank(message = "Last name cannot be empty")
    @Schema(description="Lastname", example = "PurgalEdited")
    private String lastname;
}
