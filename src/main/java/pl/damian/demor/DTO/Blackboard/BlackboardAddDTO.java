package pl.damian.demor.DTO.Blackboard;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardAddDTO {

    @NotBlank
    @Size(min = 3, max = 50,
            message = "name must be between 3 and 50 characters")
    private String name;

    private String description;

    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$",
            message = "Invalid hex color")
    private String color;
}
