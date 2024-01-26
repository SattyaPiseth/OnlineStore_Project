package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import co.devkh.onlinestore.reviewonlinestore.api.user.service.AuthorityService;
import co.devkh.onlinestore.reviewonlinestore.base.controller.BaseController;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authorities")
@RequiredArgsConstructor
public class AuthorityController extends BaseController {
    private final AuthorityService authorityService;
    @GetMapping
    public ResponseEntity<StructureRS> findAllAuthorities(@RequestParam(value = "page", required = false) int page, @RequestParam int size){
        var pageable = Pageable.ofSize(size).withPage(page);
        var authorityDtoPage = authorityService.findAllAuthorities(pageable);
        return response(authorityDtoPage);
    }
}
