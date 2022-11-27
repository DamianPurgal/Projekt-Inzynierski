package pl.damian.demor.DTO.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.damian.demor.DTO.appUser.AppUserDTO;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private UUID uuid;
    private String name;
    private String description;
    private String color;
    private Integer position;
    private AppUserDTO user;

}
