package co.devkh.onlinestore.reviewonlinestore.api.user.mapper;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.Role;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.role_dto.NewRoleDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.role_dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role fromNewRoleDto(NewRoleDto newRoleDto);
    RoleDto toRoleDto(Role role);

    void fromNewRoleDto(NewRoleDto roleDto, @MappingTarget Role role);
}
