package co.devkh.onlinestore.reviewonlinestore.api.user.web.authority_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record AssignAuthorityDto(
        @NotNull
        @Size(min = 1)
        Set<Integer> authorityIds
) {
}
