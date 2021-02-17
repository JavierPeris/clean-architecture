package org.javierperis.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceCalculationInteractorTest {

    private static final Long ZARA_BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
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
    @Mock
    private PricePresenter pricePresenter;

    @InjectMocks
    PriceCalculationInteractor priceCalculationInteractor;

    @BeforeEach
    void setUp() {
        priceCalculationInteractor = new PriceCalculationInteractor(priceCalculationDsGateway, pricePresenter);
    }

    @Test
    public void givenJustOnePriceInRange_whenRequest_ThenReturnThatPrice() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 16).atTime(21, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);
        final List<PriceDsResponseModel> prices = List.of(createPriceDsResponse(FIRST_PRICE_LIST,
                firstStartDate, firstEndDate, 0L, FIRST_PRICE));
        final PriceResponseModel expectedPriceResponseModel = createPriceResponseModel(FIRST_PRICE_LIST,
                firstStartDate, firstEndDate, FIRST_PRICE);
        when(priceCalculationDsGateway.getPrices(any())).thenReturn(prices);

        priceCalculationInteractor.getPrice(priceRequestModel);

        verify(pricePresenter, times(1)).prepareSuccessView(expectedPriceResponseModel);
    }

    @Test
    public void givenSomePricesInRange_whenRequest_ThenReturnPriceWithHigherPriority() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14).atTime(16, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);
        final List<PriceDsResponseModel> prices = List.of(
                createPriceDsResponse(FIRST_PRICE_LIST, firstStartDate, firstEndDate, 0L, FIRST_PRICE),
                createPriceDsResponse(SECOND_PRICE_LIST, secondStartDate, secondEndDate, 1L, SECOND_PRICE));
        final PriceResponseModel expectedPriceResponseModel = createPriceResponseModel(SECOND_PRICE_LIST,
                secondStartDate, secondEndDate, SECOND_PRICE);
        when(priceCalculationDsGateway.getPrices(any())).thenReturn(prices);

        priceCalculationInteractor.getPrice(priceRequestModel);
        verify(pricePresenter, times(1)).prepareSuccessView(expectedPriceResponseModel);
    }

    @Test
    public void givenNoPriceInRage_whenRequest_ThenReturnError() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14).atTime(16, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1L, 35455L, localDateTime);

        when(priceCalculationDsGateway.getPrices(any())).thenReturn(new ArrayList<>());

        priceCalculationInteractor.getPrice(priceRequestModel);
        verify(pricePresenter, times(1))
                .prepareFailView("There's no price for the product and date specified");
    }

    private PriceDsResponseModel createPriceDsResponse(Long priceList, LocalDateTime startDateForApplicablePrice,
                                                       LocalDateTime endDateForApplicablePrice, Long priority,
                                                       double expectedPrice) {
        return new PriceDsResponseModel(PRODUCT_ID, ZARA_BRAND_ID, priceList,
                startDateForApplicablePrice, endDateForApplicablePrice, priority, expectedPrice, "EUR");
    }

    private PriceResponseModel createPriceResponseModel(Long priceList, LocalDateTime startDateForPrice,
                                                        LocalDateTime endDateForPrice, double expectedPrice) {
        return new PriceResponseModel(PRODUCT_ID, ZARA_BRAND_ID, priceList, startDateForPrice, endDateForPrice,
                expectedPrice, Currency.getInstance("EUR"));
    }
}
