package pl.damian.demor.DTO.blackboard;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
public class BlackboardAddContributorDTO {

    @Schema(description="Blackboard ID", example = "1")
    private Long blackboardId;

    @Schema(description="Owner username (email)", example = "DamianPurgal5@gmail.com")
    private String ownerUsername;

    @Schema(description="Contributor username (email)", example = "XXX@gmail.com")
    private String contributorUsername;
}
