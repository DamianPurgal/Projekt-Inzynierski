package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import pl.damian.demor.DTO.AppUser.AppUserDTO;
import pl.damian.demor.DTO.AppUser.RegisterAppUserDTO;
import pl.damian.demor.model.AppUser;

@Mapper()
public interface AppUserMapper {

    AppUser mapRegisterAppUserDtoToAppuser(RegisterAppUserDTO registerAppUserDTO);

    AppUser mapAppUserDtoToAppUser(AppUserDTO appUserDTO);

    AppUserDTO mapAppUserToAppUserDTO(AppUser appUser);
}
