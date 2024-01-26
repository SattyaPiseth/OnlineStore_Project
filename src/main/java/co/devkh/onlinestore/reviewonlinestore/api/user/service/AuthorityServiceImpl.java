package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.*;
import co.devkh.onlinestore.reviewonlinestore.api.user.mapper.AuthorityMapper;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.authority_dto.AuthorityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService{
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    @Override
    public Page<AuthorityDto> findAllAuthorities(Pageable pageable) {
       return authorityRepository.findAll(pageable).map(authorityMapper::toAuthorityDto);
    }
}

