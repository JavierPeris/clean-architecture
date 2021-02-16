package org.javierperis.pricing;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PriceRequestModel {

    Long brandId;
    Long productId;
    LocalDateTime date;

}
