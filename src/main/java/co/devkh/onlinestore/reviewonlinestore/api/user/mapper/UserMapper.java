package co.devkh.onlinestore.reviewonlinestore.api.user.mapper;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.User;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.NewUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UpdateUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User fromNewUserDto(NewUserDto newUserDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateUserDto(@MappingTarget User user,UpdateUserDto updateUserDto);

}
