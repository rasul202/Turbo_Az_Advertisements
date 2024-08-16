package org.example.web_scrapt.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.model.CarAdvertisementModel;
import org.example.web_scrapt.service.CarAdvertisementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("Turbo_az_advertisements")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CarAdvertisementController {

    CarAdvertisementService carAdvertisementService;

    @GetMapping("advertisements")
    public List<CarAdvertisementModel> getCarsFromUrl() throws IOException {
        return carAdvertisementService.getCarsFromUrl("https://turbo.az/autos?q%5Bsort%5D=&q%5Bmake%5D%5B%5D=1&q%5Bmodel%5D%5B%5D=&q%5Bmodel%5D%5B%5D=787&q%5Bused%5D=&q%5Bregion%5D%5B%5D=&q%5Bregion%5D%5B%5D=1&q%5Bprice_from%5D=&q%5Bprice_to%5D=16000&q%5Bcurrency%5D=azn&q%5Bloan%5D=0&q%5Bbarter%5D=0&q%5Bcategory%5D%5B%5D=&q%5Byear_from%5D=&q%5Byear_to%5D=&q%5Bcolor%5D%5B%5D=&q%5Bfuel_type%5D%5B%5D=&q%5Bgear%5D%5B%5D=&q%5Btransmission%5D%5B%5D=&q%5Btransmission%5D%5B%5D=2&q%5Bengine_volume_from%5D=&q%5Bengine_volume_to%5D=&q%5Bpower_from%5D=&q%5Bpower_to%5D=&q%5Bmileage_from%5D=&q%5Bmileage_to%5D=&q%5Bonly_shops%5D=&q%5Bprior_owners_count%5D%5B%5D=&q%5Bseats_count%5D%5B%5D=&q%5Bmarket%5D%5B%5D=&q%5Bcrashed%5D=1&q%5Bpainted%5D=1&q%5Bfor_spare_parts%5D=0&q%5Bavailability_status%5D=");
    }

}
