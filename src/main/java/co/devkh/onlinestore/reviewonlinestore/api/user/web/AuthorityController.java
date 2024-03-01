package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import co.devkh.onlinestore.reviewonlinestore.api.user.service.AuthorityService;
import co.devkh.onlinestore.reviewonlinestore.base.controller.BaseController;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authorities")
@RequiredArgsConstructor
public class AuthorityController extends BaseController {
    private final AuthorityService authorityService;
//    @PreAuthorize("hasAuthority('SCOPE_authority:read')")
    @PreAuthorize("hasAuthority('SCOPE_role:read')")
    @GetMapping
    public ResponseEntity<StructureRS> getAllAuthorities(BaseListingRQ request) {
        return response(authorityService.getAllAuthorities(request));
    }
}
