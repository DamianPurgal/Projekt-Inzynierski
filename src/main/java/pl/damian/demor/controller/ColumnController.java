package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnAddDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnDTO;
import pl.damian.demor.service.definition.ColumnService;


import java.util.UUID;

import static pl.damian.demor.util.AppUserUtil.getLoggedUserUsername;

@RestController
@AllArgsConstructor
@RequestMapping("api/blackboard/column")
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("/{blackboardUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create new column", description = "Create new column")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardColumnDTO addColumnToBlackboard(@RequestBody BlackboardColumnAddDTO columnAddDTO,
                                                     @RequestParam UUID blackboardUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return columnService.addColumnToBlackboard(
                loggedUserUsername,
                columnAddDTO,
                blackboardUUID
        );
    }

}
