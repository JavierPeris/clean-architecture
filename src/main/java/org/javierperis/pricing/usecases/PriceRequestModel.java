package org.javierperis.pricing.usecases;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PriceRequestModel {

    Integer brandId;
    Long productId;
    LocalDateTime date;

}
