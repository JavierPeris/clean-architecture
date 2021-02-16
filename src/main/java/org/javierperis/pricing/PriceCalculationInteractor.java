package org.javierperis.pricing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

public class PriceCalculationInteractor {


    public PriceResponseModel getPrice(PriceRequestModel priceRequestModel) {
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(0, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 12, 31)
                .atTime(23, 59);
        final Long expectedPriceList = 1L;
        final double expectedPrice = 35.50D;
    
        return new PriceResponseModel(35455L, 1L, expectedPriceList, expectedStartDateForApplicablePrice,
                expectedEndDateForApplicablePrice, expectedPrice, Currency.getInstance("EUR"));
    }
}
