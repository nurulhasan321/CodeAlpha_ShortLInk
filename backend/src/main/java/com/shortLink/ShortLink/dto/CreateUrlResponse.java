package com.shortLink.ShortLink.dto;

public record CreateUrlResponse(
        String shortUrl,
        String originalUrl,
        String dateTime,
        Long clickCount
) {
}
