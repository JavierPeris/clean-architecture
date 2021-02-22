package org.javierperis.pricing.usecases.models;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Currency;

@Value
public class PriceResponseModel {

    Long productId;
    Integer brandId;
    Integer priceList;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Double price;
    Currency currency;
}
