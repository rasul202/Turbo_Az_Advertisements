package org.example.web_scrapt.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllCarAdvertisementsResponse {

    String carName;
    BigDecimal price;
    String currency;
    String details;
    String dateTime;

    @Override
    public String toString() {
        return "\n{\n" +
                "car name='" + carName + "\n" +
                "price=" + price + currency + "\n" +
                "details='" + details + "\n" +
                "dateTime='" + dateTime + "\n" +
                "}\n";
    }
}
