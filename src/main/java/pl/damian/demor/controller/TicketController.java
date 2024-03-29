package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.ticket.TicketAddDTO;
import pl.damian.demor.DTO.ticket.TicketDTO;
import pl.damian.demor.DTO.ticket.TicketDetailedDTO;
import pl.damian.demor.DTO.ticket.TicketEditDTO;
import pl.damian.demor.service.definition.TicketService;
import pl.damian.demor.service.definition.model.ColumnPath;
import pl.damian.demor.service.definition.model.TicketPath;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.UUID;

import static pl.damian.demor.util.AppUserUtil.getLoggedUserUsername;

@RestController
@AllArgsConstructor
@RequestMapping("api/blackboards/")
@CrossOrigin
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{blackboardUUID}/columns/{columnUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create new ticket", description = "Create new ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public TicketDTO addTicketToColumn(@RequestBody TicketAddDTO ticketAddDTO,
                                       @PathVariable UUID blackboardUUID,
                                       @PathVariable UUID columnUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return ticketService.addTicketToColumn(
                loggedUserUsername,
                ColumnPath.builder()
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build(),
                ticketAddDTO
        );
    }

    @DeleteMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete ticket", description = "Delete ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteTicket(@PathVariable UUID blackboardUUID,
                             @PathVariable UUID columnUUID,
                             @PathVariable UUID ticketUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        ticketService.deleteTicket(
                loggedUserUsername,
                TicketPath.builder()
                        .ticketUUID(ticketUUID)
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build()
        );
    }

    @GetMapping("/{blackboardUUID}/columns/{columnUUID}/tickets")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get all tickets of column", description = "Get all tickets of column")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<TicketDTO> getAllTicketsOfColumn(@PathVariable UUID blackboardUUID,
                                                 @PathVariable UUID columnUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return ticketService.getAllTicketsOfColumn(
                loggedUserUsername,
                ColumnPath.builder()
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build()
        );
    }

    @GetMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}/detailed")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get detailed information about ticket", description = "Get detailed information about ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public TicketDetailedDTO getDetailedTicket(@PathVariable UUID blackboardUUID,
                                               @PathVariable UUID columnUUID,
                                               @PathVariable UUID ticketUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return ticketService.getTicketDetailed(
                loggedUserUsername,
                TicketPath.builder()
                        .blackboardUUID(blackboardUUID)
                        .columnUUID(columnUUID)
                        .ticketUUID(ticketUUID)
                        .build()
        );
    }

    @PutMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Edit ticket", description = "Edit ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public TicketDTO editTicket(@RequestBody TicketEditDTO ticketEditDTO,
                                @PathVariable UUID blackboardUUID,
                                @PathVariable UUID columnUUID,
                                @PathVariable UUID ticketUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return ticketService.editTicket(
                loggedUserUsername,
                TicketPath.builder()
                        .ticketUUID(ticketUUID)
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build(),
                ticketEditDTO
        );
    }


    @PutMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}/changePosition")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Change ticket position", description = "Change ticket position")
    @SecurityRequirement(name = "Bearer Authentication")
    public TicketDTO changeTicketColumn(@RequestParam Integer newPosition,
                                        @RequestParam UUID newColumnUUID,
                                          @PathVariable UUID blackboardUUID,
                                          @PathVariable UUID columnUUID,
                                          @PathVariable UUID ticketUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return ticketService.changeTicketPosition(
                loggedUserUsername,
                TicketPath.builder()
                        .ticketUUID(ticketUUID)
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build(),
                newPosition,
                newColumnUUID
        );
    }

    @PostMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}/assignContributor")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Assign contributor to ticket", description = "Assign contributor to ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public TicketDTO assignContributorToTicket(@PathVariable UUID blackboardUUID,
                                               @PathVariable UUID columnUUID,
                                               @PathVariable UUID ticketUUID,
                                               @RequestParam @Email String contributor) {
        String loggedUserUsername = getLoggedUserUsername();

        return ticketService.assignUserToTicket(
                loggedUserUsername,
                contributor,
                TicketPath.builder()
                        .blackboardUUID(blackboardUUID)
                        .columnUUID(columnUUID)
                        .ticketUUID(ticketUUID)
                        .build()
        );
    }

    @PostMapping("/{blackboardUUID}/columns/{columnUUID}/tickets/{ticketUUID}/removeAssigment")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Remove contributor assigment to ticket", description = "Remove contributor assigment to ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public TicketDTO removeUserAssigmentToTicket(@PathVariable UUID blackboardUUID,
                                               @PathVariable UUID columnUUID,
                                               @PathVariable UUID ticketUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return ticketService.removeUserAssigmentToTicket(
                loggedUserUsername,
                TicketPath.builder()
                        .blackboardUUID(blackboardUUID)
                        .columnUUID(columnUUID)
                        .ticketUUID(ticketUUID)
                        .build()
        );
    }

}
