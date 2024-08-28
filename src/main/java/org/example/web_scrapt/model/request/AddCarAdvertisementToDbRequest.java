package org.example.web_scrapt.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class AddCarAdvertisementToDbRequest {

    String carName;
    BigDecimal price;
    String currency;
    String details;
    String dateTime;
    String uniqueIdentifier;

}
