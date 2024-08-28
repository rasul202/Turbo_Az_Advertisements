package org.example.web_scrapt.service.host;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.web_scrapt.constants.StatusConstants;
import org.example.web_scrapt.entity.CarAdvertisementEntity;
import org.example.web_scrapt.entity.UrlEntity;
import org.example.web_scrapt.mapper.CarAdvertisementMapper;
import org.example.web_scrapt.model.response.GetAllCarAdvertisementsResponse;
import org.example.web_scrapt.repositories.CarAdvertisementsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class CarAdvertisementService {

    CarAdvertisementsRepository carAdvertisementsRepository;
    UrlService urlService;
    CarAdvertisementMapper carAdvertisementMapper;

    public List<CarAdvertisementEntity> fetchAllCarsFromUrl(String url)  {

        Elements requiredElements = getElementContainsAdvertisementsFromUrl(url);
        ArrayList<CarAdvertisementEntity> carList = new ArrayList<>();

        for (Element element : requiredElements) {

            CarAdvertisementEntity car = new CarAdvertisementEntity();

            Element name = element.selectFirst("div.products-i__name");
            Element details = element.selectFirst("div.products-i__attributes");
            Element price = element.selectFirst("div.product-price");
            Element currency = price.selectFirst("span");
            Element dateTime = element.selectFirst("div.products-i__datetime");
            String link = element.selectFirst("a").attr("href");
            String[] parts = link.split("/|-");
            String uniqueIdentifier = parts[2];


            try {
                car.setCarName(name.text());
                car.setDetails(details.text());
                car.setPrice(BigDecimal.valueOf(Long.valueOf(price.ownText().trim().replace(" ",""))));
                car.setCurrency(currency.text());
                car.setDateTime(dateTime.text());
                car.setUniqueIdentifier(uniqueIdentifier);
            } catch (NullPointerException e) {
                System.out.println("there ");
                throw new NullPointerException("element of advertisement is null ");
            }

            carList.add(car);
        }
        return carList;
    }

    private Elements getElementContainsAdvertisementsFromUrl(String url) {
        //will get full html source of this url
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException("there is a problem while fetching data from url");
        }

        //this Element will be Element that hold all required Elements
        Element productContainer = null;

        Elements titleElements = document.select("div.section-title.section-title--grey");
        for (Element element : titleElements){
            String titleText = element.selectFirst("p.section-title_name").text();
            if(titleText.equals("ELANLAR")){
                //l have chosen the next element , if you look at html of this page you can see the container of advertisements is located under the title
                productContainer = element.nextElementSibling();
                break;
            }
        }
        return productContainer.select("div.products-i"); //+":not(.products-i--promotion-card)"
    }

    public List<GetAllCarAdvertisementsResponse> getAllCarsFromUrl(String url) throws IOException {
        return fetchAllCarsFromUrl(url).stream()
                .map(carAdvertisementMapper::toGetAllCarsAdvertisementsResponse)
                .collect(Collectors.toList());
    }

    public List<GetAllCarAdvertisementsResponse> getAllCarsFromDb(String url){
        return carAdvertisementsRepository.findAll().stream()
                .map(carAdvertisementMapper::toGetAllCarsAdvertisementsResponse)
                .collect(Collectors.toList());
    }

    public void addAllCarAdvertisementsFromUrlToDbOrUpdateChangedAdvertisements(String url)  {

        Optional<UrlEntity> optionalUrl = urlService.getByUrl(url);

        List<CarAdvertisementEntity> carsFromUrl = fetchAllCarsFromUrl(url);

        if (optionalUrl.isEmpty()){
            addAllCarsFromUrlToDb(url , carsFromUrl);
        }else{
            updateChangedAdvertisementsInDb(carsFromUrl , url);
        }
    }

    private void addAllCarsFromUrlToDb(String url , List<CarAdvertisementEntity> carsFromUrl){
        UrlEntity urlEntity = UrlEntity.builder().url(url).build();

        List<CarAdvertisementEntity> cars = carsFromUrl.stream()
                .peek(carFromUrl -> carFromUrl.setUrl(urlEntity))
                .collect(Collectors.toList());

        urlService.saveUrlEntity(urlEntity);
        carAdvertisementsRepository.saveAll(cars);
    }


    private CarAdvertisementEntity getCarByUniqueIdentifier(String identifier){
        return carAdvertisementsRepository.getByUniqueIdentifierAndStatus(identifier , StatusConstants.ACTIVE.getStatus())
                .orElseThrow(() -> new RuntimeException("there is no car Advertisement with "+ identifier +"-unique_identifier"));
    }

    //returns new cars in url
    public List<CarAdvertisementEntity> updateChangedAdvertisementsInDb(List<CarAdvertisementEntity> carsFromUrl , String url){

        List<CarAdvertisementEntity> newCars = new ArrayList<>();
            for (CarAdvertisementEntity carFromUrl : carsFromUrl){
                String identifier = carFromUrl.getUniqueIdentifier();
                Optional<CarAdvertisementEntity> optionalCar = carAdvertisementsRepository.getByUniqueIdentifierAndStatus(identifier,StatusConstants.ACTIVE.getStatus());

                if (optionalCar.isEmpty()) {
                    newCars.add(carFromUrl);
                    continue;
                }
                CarAdvertisementEntity carFromDb = optionalCar.get();

                carAdvertisementMapper.updateCarAdvertisementEntity(carFromDb , carFromUrl);
                carFromDb.getCurrency();
                carFromUrl.getCurrency();
                carAdvertisementsRepository.save(carFromDb);
            }
            //we are sure abut this urlEntity is exist (about get() method)
            UrlEntity urlEntity = urlService.getByUrl(url).get();
            newCars.stream().peek((car)-> car.setUrl(urlEntity)).toList();
            carAdvertisementsRepository.saveAll(newCars);
            return newCars;
    }

}
