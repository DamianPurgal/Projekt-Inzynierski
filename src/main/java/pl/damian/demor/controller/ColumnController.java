package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnAddDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnEditDTO;
import pl.damian.demor.service.definition.columnService.ColumnService;
import pl.damian.demor.service.definition.columnService.model.ColumnPath;


import java.util.List;
import java.util.UUID;

import static pl.damian.demor.util.AppUserUtil.getLoggedUserUsername;

@RestController
@AllArgsConstructor
@RequestMapping("api/blackboards/")
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("/{blackboardUUID}/column")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create new column", description = "Create new column")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardColumnDTO addColumnToBlackboard(@RequestBody BlackboardColumnAddDTO columnAddDTO,
                                                     @PathVariable UUID blackboardUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return columnService.addColumnToBlackboard(
                loggedUserUsername,
                columnAddDTO,
                blackboardUUID
        );
    }
    @DeleteMapping("/{blackboardUUID}/column/{columnUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete column", description = "Delete column")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteBlackboardColumn(@PathVariable UUID blackboardUUID,
                                       @PathVariable UUID columnUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        columnService.deleteColumn(
                loggedUserUsername,
                ColumnPath.builder()
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build()
        );
    }

    @GetMapping("/{blackboardUUID}/column")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get all columns of blackboard", description = "Get all columns of blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<BlackboardColumnDTO> getAllColumnsOfBlackboard(@PathVariable UUID blackboardUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return columnService.getAllColumnsOfBlackboard(
                loggedUserUsername,
                blackboardUUID
        );
    }

    @PutMapping("/{blackboardUUID}/column/{columnUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Edit column", description = "Edit column")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardColumnDTO editColumnOfBlackboard(@RequestBody BlackboardColumnEditDTO blackboardColumnEditDTO,
                                                      @PathVariable UUID blackboardUUID,
                                                      @PathVariable UUID columnUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return columnService.editColumn(
                loggedUserUsername,
                ColumnPath.builder()
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build(),
                blackboardColumnEditDTO
        );
    }

    @PutMapping("/{blackboardUUID}/column/{columnUUID}/changePosition")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Change column position", description = "Change column position")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardColumnDTO editColumnOfBlackboard(@RequestParam Integer newPosition,
                                                      @PathVariable UUID blackboardUUID,
                                                      @PathVariable UUID columnUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return columnService.changeColumnPosition(
                loggedUserUsername,
                ColumnPath.builder()
                        .columnUUID(columnUUID)
                        .blackboardUUID(blackboardUUID)
                        .build(),
                newPosition
        );
    }
}
