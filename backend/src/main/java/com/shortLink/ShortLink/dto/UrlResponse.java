package com.shortLink.ShortLink.dto;

public record UrlResponse(
        String originalUrl,
        Long clickCount,
        String createdAt,
        String shortUrl
) {
}

