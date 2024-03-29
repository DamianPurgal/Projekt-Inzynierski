package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.ticket.TicketAddDTO;
import pl.damian.demor.DTO.ticket.TicketDTO;
import pl.damian.demor.DTO.ticket.TicketDetailedDTO;
import pl.damian.demor.DTO.ticket.TicketEditDTO;
import pl.damian.demor.service.definition.model.ColumnPath;
import pl.damian.demor.service.definition.model.TicketPath;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    TicketDTO addTicketToColumn(String ownerUsername, ColumnPath columnPath, TicketAddDTO ticketAddDTO);

    TicketDTO editTicket(String ownerUsername, TicketPath ticketPath, TicketEditDTO ticketEditDTO);

    void deleteTicket(String ownerUsername, TicketPath ticketPath);

    List<TicketDTO> getAllTicketsOfColumn(String ownerUsername, ColumnPath columnPath);

    TicketDTO changeTicketPosition(String ownerUsername, TicketPath ticketPath, Integer newPosition, UUID newColumnUUID);

    TicketDetailedDTO getTicketDetailed(String ownerUsername, TicketPath ticketPath);

    TicketDTO assignUserToTicket(String ownerUsername, String userToAssignUsername, TicketPath ticketPath);

    TicketDTO removeUserAssigmentToTicket(String ownerUsername, TicketPath ticketPath);
}
