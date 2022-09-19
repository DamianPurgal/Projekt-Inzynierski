package pl.damian.demor.DTO.blackboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.damian.demor.model.ContributorRole;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardDTO {

    private UUID uuid;

    private String name;

    private String description;

    private String color;

    private ContributorRole role;
}
