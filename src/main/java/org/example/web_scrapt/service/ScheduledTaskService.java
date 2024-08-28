package org.example.web_scrapt.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bouncycastle.pqc.jcajce.provider.NTRU;
import org.example.web_scrapt.entity.CarAdvertisementEntity;
import org.example.web_scrapt.entity.UrlEntity;
import org.example.web_scrapt.mapper.CarAdvertisementMapper;
import org.example.web_scrapt.service.host.CarAdvertisementService;
import org.example.web_scrapt.service.host.TelegramService;
import org.example.web_scrapt.service.host.UrlService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduledTaskService {

    final UrlService urlService;
    final TelegramService telegramService;
    final CarAdvertisementService carAdvertisementService;
    final CarAdvertisementMapper carAdvertisementMapper;

    boolean isRunning = false;

    public void startRunning(){
        isRunning = true;
    }

    public void stopRunning(){
        isRunning = false;
    }

    public boolean checkRunning(){
        return isRunning;
    }

    @Scheduled(fixedDelay = 30000)
    public void checkNewCarsInAllUrlsAndSendNotification(){
        if(isRunning == true){

            List<String> allUrls = urlService.getAllUrlEntities().stream()
                    .map(UrlEntity::getUrl)
                    .toList();

            List<CarAdvertisementEntity> newCars = null;
            for (String url : allUrls){
                List<CarAdvertisementEntity> carsFromUrl = carAdvertisementService.fetchAllCarsFromUrl(url);

                //new cars has almost been added to the db
                newCars = carAdvertisementService.updateChangedAdvertisementsInDb(carsFromUrl , url);
            }
            if( !newCars.isEmpty() ){
                telegramService.sendMessage("new cars: \n" + newCars.stream()
                        .map(carAdvertisementMapper::toGetAllCarsAdvertisementsResponse)
                        .toList());
            }
        }
    }

}
