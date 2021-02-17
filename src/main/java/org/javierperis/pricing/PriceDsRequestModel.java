package org.javierperis.pricing;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PriceDsRequestModel {

    Integer brandId;
    Long productId;
    LocalDateTime date;

}
