package org.javierperis.pricing;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PriceDsResponseModel implements Comparable<PriceDsResponseModel> {

    Long productId;
    Integer brandId;
    Integer priceList;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer priority;
    Double price;
    String currency;

    @Override
    public int compareTo(PriceDsResponseModel o) {
        return this.getPriority().compareTo(o.getPriority());
    }
}
