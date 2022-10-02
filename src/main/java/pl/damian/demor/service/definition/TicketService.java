package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.ticket.TicketAddDTO;
import pl.damian.demor.DTO.ticket.TicketDTO;
import pl.damian.demor.DTO.ticket.TicketEditDTO;
import pl.damian.demor.service.definition.model.ColumnPath;
import pl.damian.demor.service.definition.model.TicketPath;

import java.util.List;

public interface TicketService {

    TicketDTO addTicketToColumn(String ownerUsername, ColumnPath columnPath, TicketAddDTO ticketAddDTO);

    TicketDTO editTicket(String ownerUsername, TicketPath ticketPath, TicketEditDTO ticketEditDTO);

    void deleteTicket(String ownerUsername, TicketPath ticketPath);

    List<TicketDTO> getAllTicketsOfColumn(String ownerUsername, TicketPath ticketPath);

    TicketDTO changeTicketPosition(String ownerUsername, TicketPath ticketPath, Integer newPosition);

}
