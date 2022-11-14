package pl.damian.demor.DTO.contributor;

import lombok.*;
import pl.damian.demor.model.ContributorRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributorDTO {

    private String email;

    private ContributorRole role;
}
