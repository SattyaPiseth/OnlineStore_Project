package co.devkh.onlinestore.reviewonlinestore.api.file.web;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FileDto(String name,
                      String uri,
                      Long size,
                      String downloadUri,
                      String extension) {
}
