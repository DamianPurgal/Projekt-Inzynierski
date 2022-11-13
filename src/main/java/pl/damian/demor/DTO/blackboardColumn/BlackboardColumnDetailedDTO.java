package pl.damian.demor.DTO.blackboardColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.damian.demor.DTO.ticket.TicketDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardColumnDetailedDTO {

    private UUID uuid;

    private String name;

    private String color;

    private Integer position;

    private List<TicketDTO> tickets;
}
