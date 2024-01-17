package co.devkh.onlinestore.reviewonlinestore.api.user;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.Role;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.NewRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role fromNewRoleDto(NewRoleDto newRoleDto);
    NewRoleDto toNewRoleDto(Role role);

    void fromNewRoleDto(NewRoleDto newRoleDto,@MappingTarget Role role);
}
