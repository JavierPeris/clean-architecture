package org.javierperis.pricing;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Currency;

@Value
public class PriceResponseModel {

    Long productId;
    Long brandId;
    Long priceList;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Double price;
    Currency currency;
}
