package co.devkh.onlinestore.reviewonlinestore.api.auth;

import co.devkh.onlinestore.reviewonlinestore.api.auth.web.RegisterDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.NewUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    NewUserDto mapRegisterDtoToNewUserDto(RegisterDto registerDto);
}
