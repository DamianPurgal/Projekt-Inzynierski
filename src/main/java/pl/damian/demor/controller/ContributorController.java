package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.contributor.ContributorAddDTO;
import pl.damian.demor.DTO.contributor.ContributorDTO;
import pl.damian.demor.DTO.contributor.ContributorDeleteDTO;
import pl.damian.demor.service.definition.ContributorService;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.UUID;

import static pl.damian.demor.util.AppUserUtil.getLoggedUserUsername;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/contributors/blackboards")
@CrossOrigin
public class ContributorController {

    private final ContributorService contributorService;

    @PostMapping("/{blackboardUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Add contributor", description = "Add contributor to blackboard by LinkId")
    @SecurityRequirement(name = "Bearer Authentication")
    public ContributorDTO addContributorToBlackboard(@PathVariable("blackboardUUID") UUID blackboardUUID,
                                                     @RequestParam @Email String contributor)
    {
        String loggedUserUsername = getLoggedUserUsername();
        contributor = contributor.toLowerCase();

        return contributorService.addContributorToBlackboard(
                ContributorAddDTO.builder()
                        .contributorUsername(contributor)
                        .ownerUsername(loggedUserUsername)
                        .blackboardUUID(blackboardUUID)
                        .build()
        );
    }

    @DeleteMapping("/{blackboardUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Remove contributor", description = "Remove contributor of blackboard by LinkId and contributor username")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteContributorOfBlackboard(@PathVariable("blackboardUUID") UUID blackboardUUID,
                                                     @RequestParam @Email String contributor)
    {
        String loggedUserUsername = getLoggedUserUsername();
        contributor = contributor.toLowerCase();

        contributorService.deleteContributorOfBlackboard(
                ContributorDeleteDTO.builder()
                        .contributorUsername(contributor)
                        .ownerUsername(loggedUserUsername)
                        .blackboardUUID(blackboardUUID)
                        .build()
        );
    }

    @GetMapping("/{blackboardUUID}")
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get all contributors of blackboard", description = "Get all contributors of blackboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<ContributorDTO> deleteContributorOfBlackboard(@PathVariable("blackboardUUID") UUID blackboardUUID)
    {
        String loggedUserUsername = getLoggedUserUsername();

        return contributorService.getAllContributorsOfBlackboard(
                loggedUserUsername,
                blackboardUUID
        );
    }

}
