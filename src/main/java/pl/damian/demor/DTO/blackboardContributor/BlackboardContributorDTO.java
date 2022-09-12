package pl.damian.demor.DTO.blackboardContributor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.damian.demor.model.ContributorRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardContributorDTO {

    @NotBlank
    private String username;

    @NotNull
    private ContributorRole contributorRole;
}
