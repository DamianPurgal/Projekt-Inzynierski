package pl.damian.demor.DTO.contributor;

import lombok.*;
import pl.damian.demor.DTO.appUser.AppUserDTO;
import pl.damian.demor.model.ContributorRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributorDTO {

    private AppUserDTO user;

    private ContributorRole role;
}
