package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.AppUser.AppUserDTO;
import pl.damian.demor.DTO.AppUser.RegisterAppUserDTO;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.service.definition.AppUserService;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public AppUserDTO registerUser(RegisterAppUserDTO user) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User %s not found", username))
                );
    }
}
