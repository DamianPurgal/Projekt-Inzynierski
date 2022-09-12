package pl.damian.demor.DTO.Blackboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.damian.demor.model.ContributorRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardDTO {

    private Long id;

    private String name;

    private String description;

    private String color;

    private ContributorRole role;
}
