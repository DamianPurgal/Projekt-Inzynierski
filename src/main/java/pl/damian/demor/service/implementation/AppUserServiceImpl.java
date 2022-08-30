package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.AppUser.AppUserDTO;
import pl.damian.demor.DTO.AppUser.RegisterAppUserDTO;
import pl.damian.demor.exception.user.UserEmailIsNotAvailableException;
import pl.damian.demor.mapper.AppUserMapper;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.security.UserRole;
import pl.damian.demor.service.definition.AppUserService;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AppUserMapper appUserMapper;

    @Override
    public AppUserDTO registerUser(RegisterAppUserDTO user) {
        if(isExistingUser(user.getEmail())){
            throw new UserEmailIsNotAvailableException();
        }

        AppUser userToRegister = appUserMapper.mapRegisterAppUserDtoToAppuser(user);

        userToRegister.setPassword(passwordEncoder.encode(user.getPassword()));
        userToRegister.setUserRole(UserRole.BASIC_USER);
        userToRegister.setLocked(false);
        userToRegister.setEnabled(true);

        AppUser registeredUser = appUserRepository.save(userToRegister);

        return appUserMapper.mapAppUserToAppUserDTO(
                registeredUser
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User %s not found", username))
                );
    }

    private boolean isExistingUser(String email){
        return appUserRepository.findByEmail(email).isPresent();
    }
}
