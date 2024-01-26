package co.devkh.onlinestore.reviewonlinestore.api.user.mapper;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.Authority;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.authority_dto.AuthorityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    AuthorityDto toAuthorityDto(Authority authority);
}
