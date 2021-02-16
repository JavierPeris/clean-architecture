package org.javierperis.pricing;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PriceDsResponseModel {

    Long productId;
    Long brandId;
    Long priceList;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Double price;
    String currency;

}
