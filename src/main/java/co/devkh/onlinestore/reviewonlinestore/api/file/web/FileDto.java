package co.devkh.onlinestore.reviewonlinestore.api.file.web;

import lombok.Builder;

@Builder
public record FileDto(String name,
                      String uri,
                      Long size,
                      String downloadUri,
                      String extension) {
}
