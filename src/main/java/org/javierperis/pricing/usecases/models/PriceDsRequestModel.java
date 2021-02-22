package org.javierperis.pricing.usecases.models;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PriceDsRequestModel {

    Integer brandId;
    Long productId;
    LocalDateTime date;

}
