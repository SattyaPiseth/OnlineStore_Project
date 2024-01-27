package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.*;
import co.devkh.onlinestore.reviewonlinestore.api.user.mapper.AuthorityMapper;
import co.devkh.onlinestore.reviewonlinestore.api.user.projection.AuthorityInfo;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.authority_dto.AuthorityDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorityServiceImpl extends BaseService implements AuthorityService{
    private final AuthorityRepository authorityRepository;

    @Override
    public StructureRS getAllAuthorities(BaseListingRQ request) {
        Page<AuthorityInfo> authorityInfoPage = authorityRepository.findByNameContains(request.getQuery(),request.getPageable(request.getSort(),request.getOrder()));
        return response(authorityInfoPage.getContent(),authorityInfoPage);
    }
}

