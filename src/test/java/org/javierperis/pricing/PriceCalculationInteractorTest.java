package org.javierperis.pricing;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculationInteractorTest {


    private static final Long ZARA_BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final Currency EURO_CURRENCY = Currency.getInstance("EUR");

    @Test
    public void givenJustOnePriceInRage_whenRequest_ThenReturnThatPrice() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 16)
                .atTime(21, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(0, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 12, 31)
                .atTime(23, 59);
        final int expectedPriceList = 1;
        final double expectedPrice = 35.50D;
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);
        final PriceCalculationInteractor priceCalculationInteractor = new PriceCalculationInteractor();

        PriceResponseModel priceResponseModel = priceCalculationInteractor.getPrice(priceRequestModel);

        assertThat(priceResponseModel.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(priceResponseModel.getBrandId()).isEqualTo(ZARA_BRAND_ID);
        assertThat(priceResponseModel.getPriceList()).isEqualTo(expectedPriceList);
        assertThat(priceResponseModel.getStartDate()).isEqualTo(expectedStartDateForApplicablePrice);
        assertThat(priceResponseModel.getEndDate()).isEqualTo(expectedEndDateForApplicablePrice);
        assertThat(priceResponseModel.getPrice()).isEqualTo(expectedPrice);
        assertThat(priceResponseModel.getCurrency()).isEqualTo(EURO_CURRENCY);
    }

    @Disabled("To be met")
    @Test
    public void givenSomePricesInRage_whenRequest_ThenReturnPriceWithHigherPriority() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14)
                .atTime(16, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(15, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(18, 30);
        final int expectedPriceList = 2;
        final double expectedPrice = 25.45D;
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);
        final PriceCalculationInteractor priceCalculationInteractor = new PriceCalculationInteractor();

        PriceResponseModel priceResponseModel = priceCalculationInteractor.getPrice(priceRequestModel);

        assertThat(priceResponseModel.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(priceResponseModel.getBrandId()).isEqualTo(ZARA_BRAND_ID);
        assertThat(priceResponseModel.getPriceList()).isEqualTo(expectedPriceList);
        assertThat(priceResponseModel.getStartDate()).isEqualTo(expectedStartDateForApplicablePrice);
        assertThat(priceResponseModel.getEndDate()).isEqualTo(expectedEndDateForApplicablePrice);
        assertThat(priceResponseModel.getPrice()).isEqualTo(expectedPrice);
        assertThat(priceResponseModel.getCurrency()).isEqualTo(EURO_CURRENCY);
    }

}
