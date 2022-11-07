package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.blackboard.*;
import pl.damian.demor.mapper.BlackboardMapper;
import pl.damian.demor.service.definition.BlackboardService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static pl.damian.demor.util.AppUserUtil.getLoggedUserUsername;

@RestController
@AllArgsConstructor
@RequestMapping("api/blackboards")
@CrossOrigin
public class BlackboardController {

    private final BlackboardService blackboardService;

    private final BlackboardMapper blackboardMapper;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create new blackboard", description = "Create new blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardDTO addBlackboard(@Valid @RequestBody BlackboardAddDTO blackboardAddDTO) {
        String loggedUserUsername = getLoggedUserUsername();

        return blackboardService.createBlackboard(
                blackboardMapper.mapBlackBoardAddDtoToBlackboardDto(
                        blackboardAddDTO
                ),
                loggedUserUsername
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get all blackboards of user", description = "Get all blackboards of user")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<BlackboardDTO> getAllUserBlackboards() {
        String loggedUserUsername = getLoggedUserUsername();

        return blackboardService.getAllBlackboardsOfUser(loggedUserUsername);
    }

    @GetMapping("/{blackboardUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get blackboard informations", description = "Get blackboard informations")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardDTO getBlackboardInformations(@PathVariable("blackboardUUID") UUID blackboardUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        return blackboardService.getBlackboardInformations(
                blackboardUUID,
                loggedUserUsername
        );
    }

    @PutMapping("/{blackboardUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Edit blackboard", description = "Edit blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardDTO editBlackboard(@PathVariable("blackboardUUID") UUID blackboardUUID,
                                           @RequestBody BlackboardEditDTO blackboardEditDTO)
    {
        String loggedUserUsername = getLoggedUserUsername();

        return blackboardService.editBlackboard(
                blackboardEditDTO,
                loggedUserUsername,
                blackboardUUID
        );
    }

    @DeleteMapping("/{blackboardUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete blackboard", description = "Delete blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteBlackboard(@PathVariable("blackboardUUID") UUID blackboardUUID) {
        String loggedUserUsername = getLoggedUserUsername();

        blackboardService.deleteBlackboard(
                blackboardUUID,
                loggedUserUsername
        );
    }

}
