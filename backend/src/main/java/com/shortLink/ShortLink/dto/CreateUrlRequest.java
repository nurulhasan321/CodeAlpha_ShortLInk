package com.shortLink.ShortLink.dto;
import jakarta.validation.constraints.NotBlank;

public record CreateUrlRequest(
        @NotBlank(message="URL cannot be empty")
        String originalUrl
) {
}
