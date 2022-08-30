package pl.damian.demor.AppUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import pl.damian.demor.DTO.AppUser.AppUserDTO;
import pl.damian.demor.DTO.AppUser.RegisterAppUserDTO;
import pl.damian.demor.mapper.AppUserMapper;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.service.definition.AppUserService;
import pl.damian.demor.service.implementation.AppUserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AppUserServiceTests {
//
//    @Mock
//    private AppUserRepository appUserRepository;
//
//    @SpyBean
//    @MockBean
//    private AppUserMapper appUserMapper;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private AppUserServiceImpl appUserService;
//
//
//    @Test
//    public void givenUserRegistrationRequest_whenRegisterUser_thenShouldSucceed(){
//        //given
//        RegisterAppUserDTO userRegisterRequest = RegisterAppUserDTO.builder()
//                .firstname("Damian")
//                .lastname("Purgal")
//                .email("DamianPurgal5@gmail.com")
//                .password("mojehaslo123")
//                .build();
//
//        when(appUserRepository.findByEmail(userRegisterRequest.getEmail())).thenReturn(Optional.empty());
//        when(appUserRepository.save(any())).thenReturn(
//                AppUser.builder()
//                        .id(1L)
//                        .email(userRegisterRequest.getEmail())
//                        .firstname(userRegisterRequest.getFirstname())
//                        .lastname(userRegisterRequest.getLastname())
//                        .password(userRegisterRequest.getPassword())
//                        .build()
//        );
//        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//
//        //when
//        AppUserDTO registeredUser = appUserService.registerUser(userRegisterRequest);
//
//        //then
//        assertEquals(
//                userRegisterRequest.getEmail(),
//                registeredUser.getEmail(),
//                "Registered user email should be the same as in request"
//        );
//        assertEquals(
//                userRegisterRequest.getFirstname(),
//                registeredUser.getFirstname(),
//                "Registered user email should be the same as in request"
//        );
//        assertEquals(
//                userRegisterRequest.getLastname(),
//                registeredUser.getLastname(),
//                "Registered user email should be the same as in request"
//        );
//    }
}
