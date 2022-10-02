package pl.damian.demor.DTO.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEditDTO {

    private String name;

    private String description;

    private String color;

    private Integer position;
}