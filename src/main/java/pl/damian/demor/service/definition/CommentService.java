package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.comment.CommentAddDTO;
import pl.damian.demor.DTO.comment.CommentDTO;
import pl.damian.demor.service.definition.model.TicketPath;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<CommentDTO> getAllCommentsOfTicket(String ownerUsername, TicketPath ticketPath);

    CommentDTO addCommentToTicket(String ownerUsername, CommentAddDTO commentAddDTO, TicketPath ticketPath);

    void deleteComment(String ownerUsername, UUID commentUUID);

}
