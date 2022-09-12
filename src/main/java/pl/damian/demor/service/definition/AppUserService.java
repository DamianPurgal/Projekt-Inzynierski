package pl.damian.demor.service.definition;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.damian.demor.DTO.appUser.AppUserDTO;
import pl.damian.demor.DTO.appUser.EditAppUserDTO;
import pl.damian.demor.DTO.appUser.RegisterAppUserDTO;

public interface AppUserService extends UserDetailsService {

    AppUserDTO registerUser(RegisterAppUserDTO user);

    AppUserDTO getUserByEmail(String email);

    AppUserDTO updateUserByEmail(String email, EditAppUserDTO editAppUserDTO);

    void deleteUserByEmail(String email);

}
