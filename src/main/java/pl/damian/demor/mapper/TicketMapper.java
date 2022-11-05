package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import pl.damian.demor.DTO.ticket.TicketAddDTO;
import pl.damian.demor.DTO.ticket.TicketDTO;
import pl.damian.demor.model.Ticket;

@Mapper()
public interface TicketMapper {

    TicketDTO mapTicketToTicketDto(Ticket ticket);

    Ticket mapTicketDtoToTicket(TicketDTO ticketDTO);

    Ticket mapTicketAddDtoToTicket(TicketAddDTO ticketAddDTO);

}
