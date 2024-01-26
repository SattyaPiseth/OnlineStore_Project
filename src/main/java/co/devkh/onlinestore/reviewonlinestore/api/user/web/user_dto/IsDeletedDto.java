package co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto;

import jakarta.validation.constraints.NotNull;

public record IsDeletedDto(@NotNull Boolean isDeleted) {
}
