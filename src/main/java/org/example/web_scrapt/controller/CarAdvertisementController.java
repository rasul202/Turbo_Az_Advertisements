package org.example.web_scrapt.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.model.request.UrlHolderRequest;
import org.example.web_scrapt.model.response.GetAllCarAdvertisementsResponse;
import org.example.web_scrapt.service.host.CarAdvertisementService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("car-advertisements")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CarAdvertisementController {

    CarAdvertisementService carAdvertisementService;

    @GetMapping("from-url")
    public List<GetAllCarAdvertisementsResponse> getCarsFromUrl(@RequestBody UrlHolderRequest urlHolder) throws IOException {
        return carAdvertisementService.getAllCarsFromUrl(urlHolder.getUrl());
    }
    @GetMapping("from-db")
    public List<GetAllCarAdvertisementsResponse> getCarsFromDb(@RequestBody UrlHolderRequest urlHolder) throws IOException {
        return carAdvertisementService.getAllCarsFromDb(urlHolder.getUrl());
    }

    @PostMapping("all-from-url-to-db")
    public void addAllCarAdvertisementsFromUrlToDbOrUpdateChangedAdvertisements(@RequestBody UrlHolderRequest urlHolder){
        carAdvertisementService.addAllCarAdvertisementsFromUrlToDbOrUpdateChangedAdvertisements(urlHolder.getUrl());
    }

//    @PutMapping("update")
//    public void updateChangedAdvertisementsInDb(@RequestBody UrlHolderRequest urlHolder){
//        carAdvertisementService.updateChangedAdvertisementsInDb(urlHolder.getUrl());
//    }


}
