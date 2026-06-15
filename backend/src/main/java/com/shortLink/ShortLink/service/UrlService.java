package com.shortLink.ShortLink.service;
import com.shortLink.ShortLink.dto.CreateUrlRequest;
import com.shortLink.ShortLink.dto.CreateUrlResponse;
import com.shortLink.ShortLink.dto.UrlResponse;
import com.shortLink.ShortLink.entity.Url;
import com.shortLink.ShortLink.repository.UrlRepository;
import com.shortLink.ShortLink.util.Base62Util;
import com.shortLink.ShortLink.util.UrlNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    public CreateUrlResponse createShortCode(CreateUrlRequest request){

        Url url = Url.builder ()
                .originalUrl (request.originalUrl ())
                .build ();

        Url savedUrl = urlRepo.save (url);
        String code = "SL"+Base62Util.encode (savedUrl.getId ());
        savedUrl.setShortCode (code);
        urlRepo.save (savedUrl);

        return new CreateUrlResponse(
                "http://localhost:8080/"+savedUrl.getShortCode (),
                savedUrl.getOriginalUrl (),
                savedUrl.getCreatedAt ().format (formatter),
                savedUrl.getClickCount ()
        );
    }


    @Transactional
    public String resolveUrl(String shortCode){

        Optional<Url> optionalUrl = urlRepo.findByShortCode (shortCode);
        Url url = optionalUrl.orElse (null);
        System.out.println (url);
        if(url == null) {
            throw new UrlNotFoundException ("Url not found");
        }
        urlRepo.incrementClick (shortCode);

        return url.getOriginalUrl ();
    }




    public List<UrlResponse> getAllUrls (){
        List<Url> urls = urlRepo.findAll ();

        return urls.stream ().map (url -> new UrlResponse (
                url.getOriginalUrl (),
                url.getClickCount (),
                url.getCreatedAt ().format (formatter),
                "http://localhost:8080/"+url.getShortCode ()
        )).toList ();
    }




}
