package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import pl.damian.demor.DTO.appUser.AppUserDTO;
import pl.damian.demor.DTO.appUser.RegisterAppUserDTO;
import pl.damian.demor.model.AppUser;

@Mapper()
public interface AppUserMapper {

    AppUser mapRegisterAppUserDtoToAppuser(RegisterAppUserDTO registerAppUserDTO);

    AppUser mapAppUserDtoToAppUser(AppUserDTO appUserDTO);

    AppUserDTO mapAppUserToAppUserDTO(AppUser appUser);
}
