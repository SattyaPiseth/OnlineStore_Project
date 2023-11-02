package co.devkh.onlinestore.reviewonlinestore.api.user.web;

import jakarta.validation.constraints.NotNull;

public record IsDeletedDto(@NotNull Boolean isDeleted) {
}
