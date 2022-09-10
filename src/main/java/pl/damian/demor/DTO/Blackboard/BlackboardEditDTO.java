package pl.damian.demor.DTO.Blackboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardEditDTO {

    @Schema(description="Blackboard name", example = "my game edited")
    private String name;

    @Schema(description="Blackboard description", example = "my own game in Unity edited")
    private String description;

    @Schema(description="Blackboard color theme", example = "#00FF00")
    private String color;

    @Schema(description="Owner username (email)", example = "DamianPurgal5@gmail.com")
    private String ownerUsername;
}
