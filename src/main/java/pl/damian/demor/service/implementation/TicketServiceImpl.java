package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.ticket.TicketAddDTO;
import pl.damian.demor.DTO.ticket.TicketDTO;
import pl.damian.demor.DTO.ticket.TicketDetailedDTO;
import pl.damian.demor.DTO.ticket.TicketEditDTO;
import pl.damian.demor.exception.blackboard.BlackboardNotFoundException;
import pl.damian.demor.exception.blackboardColumn.BlackboardColumnNotFoundException;
import pl.damian.demor.exception.ticket.TicketCannotChangePositionException;
import pl.damian.demor.exception.ticket.TicketNotFoundException;
import pl.damian.demor.exception.user.UserNotFoundException;
import pl.damian.demor.mapper.TicketMapper;
import pl.damian.demor.model.*;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.TicketRepository;
import pl.damian.demor.service.definition.TicketService;
import pl.damian.demor.service.definition.model.ColumnPath;
import pl.damian.demor.service.definition.model.TicketPath;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final AppUserRepository userRepository;

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    @Override
    public TicketDTO addTicketToColumn(String ownerUsername, ColumnPath columnPath, TicketAddDTO ticketAddDTO) {
        BlackboardColumn column = findColumnOfUser(
                ownerUsername,
                columnPath
        );

        Ticket ticketToAdd = ticketMapper.mapTicketAddDtoToTicket(ticketAddDTO);
        ticketToAdd.setPosition(
                findNewTicketPositionInColumn(column)
        );
        ticketToAdd.setColumn(column);
        ticketToAdd.setUuid(UUID.randomUUID());

        return ticketMapper.mapTicketToTicketDto(
                ticketRepository.save(ticketToAdd)
        );
    }

    @Override
    @Transactional
    public TicketDTO editTicket(String ownerUsername, TicketPath ticketPath, TicketEditDTO ticketEditDTO) {
        Ticket ticketToEdit = findTicketOfUser(ownerUsername, ticketPath);

        ticketToEdit.setName(ticketEditDTO.getName());
        ticketToEdit.setDescription(ticketEditDTO.getDescription());
        ticketToEdit.setColor(ticketEditDTO.getColor());
        ticketToEdit.setUser(null);

        return ticketMapper.mapTicketToTicketDto(
                ticketRepository.save(ticketToEdit)
        );
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
    public List<TicketDTO> getAllTicketsOfColumn(String ownerUsername, ColumnPath columnPath) {
        return findColumnOfUser(
                ownerUsername,
                ColumnPath.builder()
                        .columnUUID(columnPath.getColumnUUID())
                        .blackboardUUID(columnPath.getBlackboardUUID())
                        .build()
                ).getTickets()
                .stream()
                .map(ticketMapper::mapTicketToTicketDto)
                .toList();
    }

    @Override
    public TicketDetailedDTO getTicketDetailed(String ownerUsername, TicketPath ticketPath) {
        return ticketMapper.mapTicketToDetailedDto(
                findTicketOfUser(ownerUsername, ticketPath)
        );
    }

    @Override
    @Transactional
    public TicketDTO changeTicketPosition(String ownerUsername, TicketPath ticketPath, Integer newPosition, UUID newColumnUUID) {
        if (ticketPath.getColumnUUID().equals(newColumnUUID)) {
            return changeTicketPositionInTheSameColumn(ownerUsername, ticketPath, newPosition);
        } else {
            return changeTicketPositionAndColum(ownerUsername, ticketPath, newPosition, newColumnUUID);
        }
    }

    private TicketDTO changeTicketPositionInTheSameColumn(String ownerUsername, TicketPath ticketPath, Integer newPosition) {
        Blackboard blackboard = findBlackboardOfUser(ownerUsername, ticketPath.getBlackboardUUID());
        BlackboardColumn column = findColumnOfBlackboard(blackboard, ticketPath.getColumnUUID());
        Set<Ticket> tickets = column.getTickets();
        Ticket ticketToSwap = findTicketOfColumn(column, ticketPath.getTicketUUID());

        if (newPosition > tickets.size() - 1) {
            throw new TicketCannotChangePositionException();
        }

        List<Ticket> ticketsToUpdate;

        if (newPosition < ticketToSwap.getPosition()) {
            ticketsToUpdate = tickets.stream()
                    .filter(ticket -> ticket.getPosition() >= newPosition && ticket.getPosition() < ticketToSwap.getPosition())
                    .map(ticket -> {
                        ticket.setPosition(ticket.getPosition() + 1);
                        return ticket;
                    })
                    .toList();
        } else {
            ticketsToUpdate = tickets.stream()
                    .filter(ticket -> ticket.getPosition() > ticketToSwap.getPosition() && ticket.getPosition() <= newPosition)
                    .map(ticket -> {
                        column.setPosition(ticket.getPosition() - 1);
                        return ticket;
                    })
                    .toList();
        }
        ticketToSwap.setPosition(newPosition);
        ticketRepository.saveAll(ticketsToUpdate);

        return ticketMapper.mapTicketToTicketDto(
                ticketRepository.save(ticketToSwap)
        );
    }

    private TicketDTO changeTicketPositionAndColum(String ownerUsername, TicketPath ticketPath, Integer newPosition, UUID newColumnUUID) {
        Blackboard blackboard = findBlackboardOfUser(ownerUsername, ticketPath.getBlackboardUUID());
        BlackboardColumn column = findColumnOfBlackboard(blackboard, ticketPath.getColumnUUID());
        BlackboardColumn columnToMoveTicket = findColumnOfBlackboard(blackboard, newColumnUUID);
        Set<Ticket> ticketsOfColumnToMove = columnToMoveTicket.getTickets();


        Ticket ticketToSwap = findTicketOfColumn(column, ticketPath.getTicketUUID());

        List<Ticket> ticketsToUpdate = new ArrayList<>();

        column.getTickets().stream()
                .filter(ticket -> ticket.getPosition() > ticketToSwap.getPosition())
                .forEach(ticket -> {
                    ticket.setPosition(ticket.getPosition() - 1);
                    ticketsToUpdate.add(ticket);
                });

        ticketsOfColumnToMove.stream()
                .filter(ticket -> ticket.getPosition() >= newPosition)
                .forEach(ticket -> {
                    ticket.setPosition(ticket.getPosition() + 1);
                    ticketsToUpdate.add(ticket);
                });

        ticketRepository.saveAll(ticketsToUpdate);

        ticketToSwap.setColumn(columnToMoveTicket);
        ticketToSwap.setPosition(newPosition);

        return ticketMapper.mapTicketToTicketDto(
                ticketRepository.save(ticketToSwap)
        );
    }

    @Override
    public TicketDTO assignUserToTicket(String ownerUsername, String userToAssignUsername, TicketPath ticketPath) {
        Ticket ticket = findTicketOfUser(ownerUsername, ticketPath);

        AppUser userToAssign = ticket.getColumn()
                .getBlackboard()
                .getContributors().stream()
                .map(BlackboardContributor::getUser)
                .filter(user -> user.getEmail().equals(userToAssignUsername.toLowerCase()))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);

        ticket.setUser(userToAssign);

        return ticketMapper.mapTicketToTicketDto(
                ticketRepository.save(ticket)
        );
    }

    @Override
    public TicketDTO removeUserAssigmentToTicket(String ownerUsername, TicketPath ticketPath) {
        Ticket ticket = findTicketOfUser(ownerUsername, ticketPath);

        ticket.setUser(null);

        return ticketMapper.mapTicketToTicketDto(
                ticketRepository.save(ticket)
        );
    }

    private Integer findNewTicketPositionInColumn(BlackboardColumn column) {
        OptionalInt position = column.getTickets().stream()
                .mapToInt(Ticket::getPosition)
                .max();
        if (position.isPresent()) {
            return position.getAsInt() + 1;
        } else {
            return 0;
        }
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

    private BlackboardColumn findColumnOfBlackboard(Blackboard blackboard, UUID columnUUID) {
        return blackboard.getColumns().stream()
                .filter(column -> column.getUuid().equals(columnUUID))
                .findFirst()
                .orElseThrow(BlackboardColumnNotFoundException::new);
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
