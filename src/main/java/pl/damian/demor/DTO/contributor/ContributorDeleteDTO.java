package pl.damian.demor.DTO.contributor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ContributorDeleteDTO {

    @Schema(description="Blackboard UUID", example = "d468d292-d871-4e5a-a64d-af269635f7db")
    private UUID blackboardUUID;

    @Schema(description="Owner username (email)", example = "DamianPurgal5@gmail.com")
    private String ownerUsername;

    @Schema(description="Contributor username (email)", example = "XXX@gmail.com")
    private String contributorUsername;
}
