package pl.damian.demor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.DTO.appUser.AppUserDTO;
import pl.damian.demor.DTO.appUser.RegisterAppUserDTO;
import pl.damian.demor.service.definition.AppUserService;

import javax.validation.Valid;

import static pl.damian.demor.util.AppUserUtil.getLoggedUserUsername;


@RestController
@AllArgsConstructor
@RequestMapping("api/user")
public class AppUserController {

    private AppUserService appUserService;

    @PostMapping()
    @PreAuthorize("permitAll()")
    @Operation(summary = "Register new user", description = "Register new user")
    public void registerUser(@Valid @RequestBody RegisterAppUserDTO registerAppUserDTO) {
        appUserService.registerUser(registerAppUserDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get user informations", description = "Get user")
    @SecurityRequirement(name = "Bearer Authentication")
    public AppUserDTO getUserInfo() {
        String loggedUserEmail = getLoggedUserUsername();

        return appUserService.getUserByEmail(loggedUserEmail);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Edit user informations", description = "Update user")
    @SecurityRequirement(name = "Bearer Authentication")
    public AppUserDTO editUserInfo() {
        String loggedUserEmail = getLoggedUserUsername();

        return appUserService.getUserByEmail(loggedUserEmail);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_BASIC_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete user", description = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteUser() {
        String loggedUserEmail = getLoggedUserUsername();

        appUserService.deleteUserByEmail(loggedUserEmail);
    }

}
