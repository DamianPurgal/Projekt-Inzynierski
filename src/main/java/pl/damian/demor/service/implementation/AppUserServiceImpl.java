package pl.damian.demor.service.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.AppUser.AppUserDTO;
import pl.damian.demor.DTO.AppUser.RegisterAppUserDTO;
import pl.damian.demor.service.definition.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Override
    public AppUserDTO registerUser(RegisterAppUserDTO user) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
