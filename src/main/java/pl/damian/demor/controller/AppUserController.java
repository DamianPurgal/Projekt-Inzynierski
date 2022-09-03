package pl.damian.demor.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    public AppUserDTO editUserInfo(){
        String loggedUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return appUserService.getUserByEmail(loggedUserEmail);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    public void deleteUser(){
        String loggedUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        appUserService.deleteUserByEmail(loggedUserEmail);
    }

}
