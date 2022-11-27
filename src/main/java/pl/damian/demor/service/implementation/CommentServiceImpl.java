package pl.damian.demor.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.comment.CommentAddDTO;
import pl.damian.demor.DTO.comment.CommentDTO;
import pl.damian.demor.exception.blackboard.BlackboardNotFoundException;
import pl.damian.demor.exception.blackboardColumn.BlackboardColumnNotFoundException;
import pl.damian.demor.exception.comment.CommentNotFoundException;
import pl.damian.demor.exception.ticket.TicketNotFoundException;
import pl.damian.demor.mapper.CommentMapper;
import pl.damian.demor.model.*;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.CommentRepository;
import pl.damian.demor.service.definition.CommentService;
import pl.damian.demor.service.definition.model.ColumnPath;
import pl.damian.demor.service.definition.model.TicketPath;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AppUserRepository userRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public List<CommentDTO> getAllCommentsOfTicket(String ownerUsername, TicketPath ticketPath) {
        Ticket ticket = findTicketOfUser(ownerUsername, ticketPath);

        return ticket.getComments().stream()
                .map(commentMapper::mapCommentToDto)
                .toList();
    }

    @Override
    public CommentDTO addCommentToTicket(String ownerUsername, CommentAddDTO commentAddDTO, TicketPath ticketPath) {
        Ticket ticket = findTicketOfUser(ownerUsername, ticketPath);

        Comment commentToAdd = commentMapper.mapCommentAddDtoToComment(commentAddDTO);
        commentToAdd.setTicket(ticket);
        commentToAdd.setDate(LocalDateTime.now());
        commentToAdd.setAuthor(findUserByUsername(ownerUsername));
        commentToAdd.setUuid(UUID.randomUUID());

        return commentMapper.mapCommentToDto(
                commentRepository.save(commentToAdd)
        );
    }

    @Override
    public void deleteComment(String ownerUsername, UUID commentUUID) {
        Comment commentToDelete = findCommentOfUser(ownerUsername, commentUUID);
        commentRepository.delete(
                commentToDelete
        );
    }

    private Comment findCommentOfUser(String ownerUsername, UUID commentUUID) {
        AppUser user = findUserByUsername(ownerUsername);

        return user.getComments().stream()
                .filter(comment -> comment.getUuid().equals(commentUUID))
                .findFirst()
                .orElseThrow(
                        CommentNotFoundException::new
                );
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
