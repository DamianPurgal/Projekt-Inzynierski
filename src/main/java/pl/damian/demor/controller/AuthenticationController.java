package pl.damian.demor.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.demor.DTO.AppUser.RegisterAppUserDTO;
import pl.damian.demor.service.definition.AppUserService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationController {

    private AppUserService appUserService;

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid RegisterAppUserDTO registerAppUserDTO){
        appUserService.registerUser(registerAppUserDTO);
    }
}
