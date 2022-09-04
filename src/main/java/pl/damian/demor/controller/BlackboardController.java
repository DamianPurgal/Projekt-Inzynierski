package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.demor.DTO.Blackboard.BlackboardAddDTO;
import pl.damian.demor.DTO.Blackboard.BlackboardDTO;
import pl.damian.demor.mapper.BlackboardMapper;
import pl.damian.demor.service.definition.BlackboardService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/blackboard")
public class BlackboardController {

    private final BlackboardService blackboardService;

    private final BlackboardMapper blackboardMapper;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create new blackboard", description = "Create new blackboard")
    public BlackboardDTO addBlackboard(@Valid @RequestBody BlackboardAddDTO blackboardAddDTO){
        String loggedUserUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return blackboardService.createBlackboard(
                blackboardMapper.mapBlackBoardAddDtoToBlackboardDto(
                        blackboardAddDTO
                ),
                loggedUserUsername
        );
    }
}
