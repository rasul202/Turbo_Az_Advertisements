package org.example.web_scrapt.service.host;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.constants.StatusConstants;
import org.example.web_scrapt.entity.UrlEntity;
import org.example.web_scrapt.repositories.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class UrlService {

    UrlRepository urlRepository;

    public Optional<UrlEntity> getByUrl(String url){
        return urlRepository.findByUrlAndStatus(url , StatusConstants.ACTIVE.getStatus());
    }

    public void saveUrlEntity(UrlEntity urlEntity) {
        Optional<UrlEntity> optionalUrl = getByUrl(urlEntity.getUrl());
        if(optionalUrl.isEmpty()) {
            urlRepository.save(urlEntity);
        }
    }

    public List<UrlEntity> getAllUrlEntities(){
        return urlRepository.findAllByStatus(StatusConstants.ACTIVE.getStatus())
                .orElseThrow(()->new RuntimeException("there is no Active Url in Url table"));
    }

}
