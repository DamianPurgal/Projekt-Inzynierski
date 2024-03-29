package pl.damian.demor.DTO.appUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AppUserDTO {
    private String email;
    private String firstname;
    private String lastname;
}
