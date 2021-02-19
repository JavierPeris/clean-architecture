package org.javierperis.pricing.usecases;

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
public class PricingInteractorTest {

    private static final Integer ZARA_BRAND_ID = 1;
    private static final Long PRODUCT_ID = 35455L;
    private static final LocalDateTime firstStartDate = LocalDate.of(2020, 6, 14)
            .atTime(0, 0);
    private static final LocalDateTime firstEndDate = LocalDate.of(2020, 12, 31)
            .atTime(23, 59);
    private static final Integer FIRST_PRICE_LIST = 1;
    private static final Double FIRST_PRICE = 35.50D;
    private static final LocalDateTime secondStartDate = LocalDate.of(2020, 6, 14)
            .atTime(15, 0);
    private static final LocalDateTime secondEndDate = LocalDate.of(2020, 6, 14)
            .atTime(18, 30);
    private static final Integer SECOND_PRICE_LIST = 2;
    private static final Double SECOND_PRICE = 25.45D;

    @Mock
    private PricingDsGateway pricingDsGateway;
    @Mock
    private PricingPresenter pricingPresenter;

    @InjectMocks
    PricingInteractor pricingInteractor;

    @BeforeEach
    void setUp() {
        pricingInteractor = new PricingInteractor(pricingDsGateway, pricingPresenter);
    }

    @Test
    public void givenJustOnePriceInRange_whenRequest_ThenReturnThatPrice() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 16).atTime(21, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1, 35455L, localDateTime);
        final List<PriceDsResponseModel> prices = List.of(createPriceDsResponse(FIRST_PRICE_LIST,
                firstStartDate, firstEndDate, 0, FIRST_PRICE));
        final PriceResponseModel expectedPriceResponseModel = createPriceResponseModel(FIRST_PRICE_LIST,
                firstStartDate, firstEndDate, FIRST_PRICE);
        when(pricingDsGateway.getPrices(any())).thenReturn(prices);

        pricingInteractor.getPrice(priceRequestModel);

        verify(pricingPresenter, times(1)).prepareSuccessView(expectedPriceResponseModel);
    }

    @Test
    public void givenSomePricesInRange_whenRequest_ThenReturnPriceWithHigherPriority() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14).atTime(16, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1, 35455L, localDateTime);
        final List<PriceDsResponseModel> prices = List.of(
                createPriceDsResponse(FIRST_PRICE_LIST, firstStartDate, firstEndDate, 0, FIRST_PRICE),
                createPriceDsResponse(SECOND_PRICE_LIST, secondStartDate, secondEndDate, 1, SECOND_PRICE));
        final PriceResponseModel expectedPriceResponseModel = createPriceResponseModel(SECOND_PRICE_LIST,
                secondStartDate, secondEndDate, SECOND_PRICE);
        when(pricingDsGateway.getPrices(any())).thenReturn(prices);

        pricingInteractor.getPrice(priceRequestModel);
        verify(pricingPresenter, times(1)).prepareSuccessView(expectedPriceResponseModel);
    }

    @Test
    public void givenNoPriceInRage_whenRequest_ThenReturnError() {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14).atTime(16, 0);
        final PriceRequestModel priceRequestModel = new PriceRequestModel(1, 35455L, localDateTime);

        when(pricingDsGateway.getPrices(any())).thenReturn(new ArrayList<>());

        pricingInteractor.getPrice(priceRequestModel);
        verify(pricingPresenter, times(1))
                .prepareFailView("There's no price for the product and date specified");
    }

    private PriceDsResponseModel createPriceDsResponse(Integer priceList, LocalDateTime startDateForApplicablePrice,
                                                       LocalDateTime endDateForApplicablePrice, Integer priority,
                                                       double expectedPrice) {
        return new PriceDsResponseModel(PRODUCT_ID, ZARA_BRAND_ID, priceList,
                startDateForApplicablePrice, endDateForApplicablePrice, priority, expectedPrice, "EUR");
    }

    private PriceResponseModel createPriceResponseModel(Integer priceList, LocalDateTime startDateForPrice,
                                                        LocalDateTime endDateForPrice, double expectedPrice) {
        return new PriceResponseModel(PRODUCT_ID, ZARA_BRAND_ID, priceList, startDateForPrice, endDateForPrice,
                expectedPrice, Currency.getInstance("EUR"));
    }
}
