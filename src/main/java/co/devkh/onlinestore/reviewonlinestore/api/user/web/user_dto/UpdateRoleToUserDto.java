package co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;


public record UpdateRoleToUserDto(
        @NotNull(message = "At least one role ID must be provided.")
        @Size(min = 1, message = "At least one role ID must be provided.")
        Set<Integer> roleIds
) {
}
