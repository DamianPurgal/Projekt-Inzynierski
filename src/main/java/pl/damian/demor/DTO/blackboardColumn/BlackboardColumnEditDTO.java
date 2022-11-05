package pl.damian.demor.DTO.blackboardColumn;

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
public class BlackboardColumnEditDTO {

    @NotBlank
    @Size(min = 3, max = 50,
            message = "name must be between 3 and 50 characters")
    @Schema(description="Column name", example = "my column name")
    private String name;

    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$",
            message = "Invalid hex color")
    @Schema(description="Column color theme", example = "#FF0000")
    private String color;

}
