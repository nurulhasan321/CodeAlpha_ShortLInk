package com.shortLink.ShortLink.util;

public class UrlNotFoundException extends RuntimeException{
    public UrlNotFoundException(String message) {
        super(message);
    }
}
