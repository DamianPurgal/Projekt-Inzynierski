package pl.damian.demor.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.demor.DTO.AppUser.AppUserDTO;
import pl.damian.demor.service.definition.AppUserService;

@RestController
@AllArgsConstructor
@RequestMapping("api/user")
public class AppUserController {

    private AppUserService appUserService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    public AppUserDTO getUserInfo(){
        String loggedUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return appUserService.getUserByEmail(loggedUserEmail);
    }
}
