package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.ticket.TicketAddDTO;
import pl.damian.demor.DTO.ticket.TicketDTO;
import pl.damian.demor.DTO.ticket.TicketEditDTO;
import pl.damian.demor.exception.blackboard.BlackboardNotFoundException;
import pl.damian.demor.exception.blackboardColumn.BlackboardColumnNotFoundException;
import pl.damian.demor.exception.ticket.TicketNotFoundException;
import pl.damian.demor.mapper.TicketMapper;
import pl.damian.demor.model.*;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.TicketRepository;
import pl.damian.demor.service.definition.TicketService;
import pl.damian.demor.service.definition.model.ColumnPath;
import pl.damian.demor.service.definition.model.TicketPath;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final AppUserRepository userRepository;

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    @Override
    public TicketDTO addTicketToColumn(String ownerUsername, ColumnPath columnPath, TicketAddDTO ticketAddDTO) {
        return null;
    }

    @Override
    public TicketDTO editTicket(String ownerUsername, TicketPath ticketPath, TicketEditDTO ticketEditDTO) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTicket(String ownerUsername, TicketPath ticketPath) {
        BlackboardColumn column = findColumnOfUser(
                ownerUsername,
                ColumnPath.builder()
                        .columnUUID(ticketPath.getColumnUUID())
                        .blackboardUUID(ticketPath.getBlackboardUUID())
                        .build()
        );
        Ticket ticketToDelete = findTicketOfColumn(
                column,
                ticketPath.getTicketUUID()
        );

        List<Ticket> ticketsToChangePosition = column.getTickets().stream()
                .filter(ticket -> ticket.getPosition() > ticketToDelete.getPosition())
                .map(ticket ->
                        {
                            ticket.setPosition(ticket.getPosition() - 1);
                            return ticket;
                        }
                ).toList();

        ticketRepository.delete(ticketToDelete);

        ticketRepository.saveAll(ticketsToChangePosition);
    }

    @Override
    public List<TicketDTO> getAllTicketsOfColumn(String ownerUsername, TicketPath ticketPath) {
        return null;
    }

    @Override
    public TicketDTO changeTicketPosition(String ownerUsername, TicketPath ticketPath, Integer newPosition) {
        return null;
    }

    private Ticket findTicketOfUser(String ownerUsername, TicketPath ticketPath) {
        BlackboardColumn column = findColumnOfUser(
                ownerUsername,
                ColumnPath.builder()
                        .columnUUID(ticketPath.getColumnUUID())
                        .blackboardUUID(ticketPath.getBlackboardUUID())
                        .build()
                );
        return column.getTickets().stream()
                .filter(ticket -> ticket.getUuid().equals(
                        ticketPath.getTicketUUID()
                        )
                )
                .findFirst()
                .orElseThrow(
                    TicketNotFoundException::new
                );
    }

    private Ticket findTicketOfColumn(BlackboardColumn column, UUID ticketUUID) {
        return column.getTickets().stream()
                .filter(ticket ->  ticket.getUuid().equals(ticketUUID))
                .findFirst()
                .orElseThrow(
                        TicketNotFoundException::new
                );
    }

    private BlackboardColumn findColumnOfUser(String ownerUsername, ColumnPath columnPath) {
        Blackboard blackboard = findBlackboardOfUser(
                ownerUsername,
                columnPath.getBlackboardUUID()
        );

        return blackboard.getColumns().stream()
                .filter(
                        column -> column.getUuid()
                                .equals(
                                        columnPath.getColumnUUID()
                                )
                ).findFirst()
                .orElseThrow(
                        BlackboardColumnNotFoundException::new
                );
    }

    private Blackboard findBlackboardOfUser(String ownerUsername, UUID blackboardUUID) {
        AppUser owner = findUserByUsername(ownerUsername);

        return owner.getContributes().stream()
                .map(BlackboardContributor::getBlackboard)
                .filter(
                        blackboard -> blackboard.getUuid().equals(blackboardUUID)
                ).findFirst()
                .orElseThrow(
                        BlackboardNotFoundException::new
                );
    }

    private AppUser findUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User %s not found", username))
                );
    }
}
