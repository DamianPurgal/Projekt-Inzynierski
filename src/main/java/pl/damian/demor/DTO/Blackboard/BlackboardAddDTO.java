package pl.damian.demor.DTO.Blackboard;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description="Blackboard name", example = "my game")
    private String name;

    @Schema(description="Blackboard description", example = "my own game in Unity")
    private String description;

    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$",
            message = "Invalid hex color")
    @Schema(description="Blackboard color theme", example = "#FF0000")
    private String color;
}
