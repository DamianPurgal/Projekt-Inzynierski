package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.Blackboard.BlackboardAddContributorDTO;
import pl.damian.demor.DTO.Blackboard.BlackboardAddDTO;
import pl.damian.demor.DTO.Blackboard.BlackboardDTO;
import pl.damian.demor.DTO.Blackboard.BlackboardEditDTO;
import pl.damian.demor.mapper.BlackboardMapper;
import pl.damian.demor.service.definition.BlackboardService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/blackboard")
public class BlackboardController {

    private final BlackboardService blackboardService;

    private final BlackboardMapper blackboardMapper;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create new blackboard", description = "Create new blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardDTO addBlackboard(@Valid @RequestBody BlackboardAddDTO blackboardAddDTO){
        String loggedUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return blackboardService.createBlackboard(
                blackboardMapper.mapBlackBoardAddDtoToBlackboardDto(
                        blackboardAddDTO
                ),
                loggedUserUsername
        );
    }

    @PostMapping("/{BlackboardId}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Add contributor", description = "Add contributor to blackboard by LinkId")
    @SecurityRequirement(name = "Bearer Authentication")
    public void addContributorToBlackboard(@PathVariable("BlackboardId") Long blackboardId,
                                           @RequestParam @Email String contributor)
    {
        String loggedUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        blackboardService.addContributorToBlackboard(
                BlackboardAddContributorDTO.builder()
                        .contributorUsername(contributor)
                        .ownerUsername(loggedUserUsername)
                        .blackboardId(blackboardId)
                        .build()
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get all blackboards of user", description = "Get all blackboards of user")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<BlackboardDTO> getAllUserBlackboards(){
        String loggedUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return blackboardService.getAllBlackboardsOfUser(loggedUserUsername);
    }

    @GetMapping("/{BlackboardId}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get blackboard informations", description = "Get blackboard informations")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardDTO getBlackboardInformations(@PathVariable("BlackboardId") Long blackboardId){
        String loggedUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return blackboardService.getBlackboardInformations(
                blackboardId,
                loggedUserUsername
        );
    }

    @PutMapping("/{BlackboardId}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Edit blackboard", description = "Edit blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public BlackboardDTO editBlackboard(@PathVariable("BlackboardId") Long blackboardId,
                                           @RequestBody BlackboardDTO blackboardDTO)
    {
        String loggedUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return blackboardService.editBlackboard(
                BlackboardEditDTO.builder()
                        .color(blackboardDTO.getColor())
                        .name(blackboardDTO.getName())
                        .description(blackboardDTO.getDescription())
                        .ownerUsername(loggedUserUsername)
                        .build(),
                blackboardId
        );
    }

    @DeleteMapping("/{BlackboardId}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete blackboard", description = "Delete blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteBlackboard(@PathVariable("BlackboardId") Long blackboardId){
        String loggedUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        blackboardService.deleteBlackboard(
                blackboardId,
                loggedUserUsername
        );
    }

}
