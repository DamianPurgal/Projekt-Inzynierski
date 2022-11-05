package pl.damian.demor.DTO.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEditDTO {

    @NotBlank
    @Size(min = 3, max = 50,
            message = "name must be between 3 and 50 characters")
    @Schema(description="Ticket name", example = "my ticket name")
    private String name;

    @Schema(description="Ticket description", example = "my own ticket")
    private String description;

    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$",
            message = "Invalid hex color")
    @Schema(description="Ticket color theme", example = "#FF0000")
    private String color;

}