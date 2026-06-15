package com.shortLink.ShortLink.controller;

import com.shortLink.ShortLink.dto.CreateUrlRequest;
import com.shortLink.ShortLink.dto.CreateUrlResponse;
import com.shortLink.ShortLink.dto.UrlResponse;
import com.shortLink.ShortLink.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/url")
@AllArgsConstructor
public class UrlController {


    private final UrlService urlService;

    @PostMapping("/short")
    public ResponseEntity<CreateUrlResponse> createShortUrl(@RequestBody CreateUrlRequest url){
        return ResponseEntity.ok (urlService.createShortCode (url));
    }



    @GetMapping("/getAllUrl")
    public List<UrlResponse> getAllUrls(){
    return urlService.getAllUrls ();
    }

//    @GetMapping("/")
}
