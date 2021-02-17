package org.javierperis.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceCalculationInteractorTest {

    private static final Long ZARA_BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final Currency EURO_CURRENCY = Currency.getInstance("EUR");

    private static final LocalDateTime firstStartDate = LocalDate.of(2020, 6, 14)
            .atTime(0, 0);
    private static final LocalDateTime firstEndDate = LocalDate.of(2020, 12, 31)
            .atTime(23, 59);
    private static final Long FIRST_PRICE_LIST = 1L;
    private static final Double FIRST_PRICE = 35.50D;

    private static final LocalDateTime secondStartDate = LocalDate.of(2020, 6, 14)
            .atTime(15, 0);
    private static final LocalDateTime secondEndDate = LocalDate.of(2020, 6, 14)
            .atTime(18, 30);
    private static final Long SECOND_PRICE_LIST = 2L;
    private static final Double SECOND_PRICE = 25.45D;

    @Mock
    private PriceCalculationDsGateway priceCalculationDsGateway;

    @InjectMocks
    PriceCalculationInteractor priceCalculationInteractor;

    @BeforeEach
    void setUp() {
        priceCalculationInteractor = new PriceCalculationInteractor(priceCalculationDsGateway);
    }

    @Test
    public void givenJustOnePriceInRange_whenRequest_ThenReturnThatPrice() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 16).atTime(21, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);
        final List<PriceDsResponseModel> prices = List.of(createPrice(FIRST_PRICE_LIST,
                firstStartDate, firstEndDate, 0L, FIRST_PRICE));
        when(priceCalculationDsGateway.getPrices(any())).thenReturn(prices);

        PriceResponseModel priceResponseModel = priceCalculationInteractor.getPrice(priceRequestModel);

        assertThat(priceResponseModel.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(priceResponseModel.getBrandId()).isEqualTo(ZARA_BRAND_ID);
        assertThat(priceResponseModel.getPriceList()).isEqualTo(FIRST_PRICE_LIST);
        assertThat(priceResponseModel.getStartDate()).isEqualTo(firstStartDate);
        assertThat(priceResponseModel.getEndDate()).isEqualTo(firstEndDate);
        assertThat(priceResponseModel.getPrice()).isEqualTo(FIRST_PRICE);
        assertThat(priceResponseModel.getCurrency()).isEqualTo(EURO_CURRENCY);
    }

    private PriceDsResponseModel createPrice(Long priceList, LocalDateTime expectedStartDateForApplicablePrice,
                                             LocalDateTime expectedEndDateForApplicablePrice, Long priority,
                                             double expectedPrice) {
        return new PriceDsResponseModel(PRODUCT_ID, ZARA_BRAND_ID, priceList,
                expectedStartDateForApplicablePrice, expectedEndDateForApplicablePrice, priority, expectedPrice, "EUR");
    }

    @Test
    public void givenSomePricesInRange_whenRequest_ThenReturnPriceWithHigherPriority() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14).atTime(16, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);
        final List<PriceDsResponseModel> prices = List.of(
                createPrice(FIRST_PRICE_LIST, firstStartDate, firstEndDate, 0L, FIRST_PRICE),
                createPrice(SECOND_PRICE_LIST, secondStartDate, secondEndDate, 1L, SECOND_PRICE));
        when(priceCalculationDsGateway.getPrices(any())).thenReturn(prices);

        PriceResponseModel priceResponseModel = priceCalculationInteractor.getPrice(priceRequestModel);

        assertThat(priceResponseModel.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(priceResponseModel.getBrandId()).isEqualTo(ZARA_BRAND_ID);
        assertThat(priceResponseModel.getPriceList()).isEqualTo(SECOND_PRICE_LIST);
        assertThat(priceResponseModel.getStartDate()).isEqualTo(secondStartDate);
        assertThat(priceResponseModel.getEndDate()).isEqualTo(secondEndDate);
        assertThat(priceResponseModel.getPrice()).isEqualTo(SECOND_PRICE);
        assertThat(priceResponseModel.getCurrency()).isEqualTo(EURO_CURRENCY);
    }

    @Test
    public void givenNoPriceInRage_whenRequest_ThenReturnError() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14).atTime(16, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);

        when(priceCalculationDsGateway.getPrices(any())).thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> priceCalculationInteractor.getPrice(priceRequestModel))
                .isInstanceOf(ResponseStatusException.class);
    }
}
