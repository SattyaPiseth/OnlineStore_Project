package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.api.user.web.authority_dto.AuthorityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface AuthorityService {
    /**
     *  Find all authorities with pageable (select)
     * @param pageable pageable
     * @return  page of authority dto
     */

    Page<AuthorityDto> findAllAuthorities(Pageable pageable);

}
