package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.comment.CommentAddDTO;
import pl.damian.demor.DTO.comment.CommentDTO;
import pl.damian.demor.service.definition.CommentService;
import pl.damian.demor.service.definition.model.TicketPath;

import java.util.List;
import java.util.UUID;

import static pl.damian.demor.util.AppUserUtil.getLoggedUserUsername;

@RestController
@AllArgsConstructor
@RequestMapping("api/blackboards")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}/comments")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create new comment", description = "Create new comment")
    @SecurityRequirement(name = "Bearer Authentication")
    public CommentDTO addCommentToTicket(@RequestBody CommentAddDTO commentAddDTO,
                                         @PathVariable UUID blackboardUUID,
                                         @PathVariable UUID columnUUID,
                                         @PathVariable UUID ticketUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return commentService.addCommentToTicket(
                loggedUserUsername,
                commentAddDTO,
                TicketPath.builder()
                        .blackboardUUID(blackboardUUID)
                        .columnUUID(columnUUID)
                        .ticketUUID(ticketUUID)
                        .build()
        );
    }

    @GetMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}/comments")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get comments of ticket", description = "Get comments of ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<CommentDTO> getAllCommentsOfTicket(@PathVariable UUID blackboardUUID,
                                                   @PathVariable UUID columnUUID,
                                                   @PathVariable UUID ticketUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return commentService.getAllCommentsOfTicket(
                loggedUserUsername,
                TicketPath.builder()
                        .blackboardUUID(blackboardUUID)
                        .columnUUID(columnUUID)
                        .ticketUUID(ticketUUID)
                        .build()
        );
    }

    @DeleteMapping("/comments/{commentUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get comments of ticket", description = "Get comments of ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public void getAllCommentsOfTicket(@PathVariable UUID commentUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        commentService.deleteComment(
                loggedUserUsername,
                commentUUID
        );
    }

}
