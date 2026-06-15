package com.shortLink.ShortLink.controller;

import com.shortLink.ShortLink.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@AllArgsConstructor
public class RedirectController {


    private final UrlService urlService;

    @GetMapping("/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode){
        String originalUrl = urlService.resolveUrl (shortCode);
        return new RedirectView (originalUrl);
    }
}
