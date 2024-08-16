package org.example.web_scrapt.service;

import org.example.web_scrapt.model.CarAdvertisementModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarAdvertisementService {

    public List<CarAdvertisementModel> getCarsFromUrl(String url) throws IOException {

        Document document = Jsoup.connect(url).get();

        //it chooses the title at the top of the main advertisements
        Element titleElement = document.selectFirst("div.section-title.section-title--grey:not(.section-title--top)");
        //here the main advertisements
        Element productContainer = titleElement.nextElementSibling();

        //this line separates advertisements in container
        Elements elements = productContainer.select("div.products-i:not(.products-i--promotion-card)");
        ArrayList<CarAdvertisementModel> carList = new ArrayList<>();

        for (Element element : elements) {

            CarAdvertisementModel car = new CarAdvertisementModel();

            Element name = element.selectFirst("div.products-i__name");
            Element details = element.selectFirst("div.products-i__attributes");
            Element price = element.selectFirst("div.product-price");
            Element dateTime = element.selectFirst("div.products-i__datetime");

            if (name != null)
                car.setName(name.text());
            if (details != null)
                car.setDetails(details.text());
            if (price != null)
                car.setPrice(price.text());
            if (dateTime != null)
                car.setDateTime(dateTime.text());

            carList.add(car);
        }

        return carList;
    }

}
