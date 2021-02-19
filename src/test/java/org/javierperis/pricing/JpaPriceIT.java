package org.javierperis.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JpaPriceIT {

    private static final Integer ZARA_BRAND_ID = 1;
    private static final Long PRODUCT_ID = 35455L;
    private static final LocalDateTime dateNotInDatabase = LocalDate.of(2022, 6, 14)
            .atTime(0, 0,30);
    private static final LocalDateTime dateInDatabase = LocalDate.of(2020, 6, 19)
            .atTime(0, 0);
    private static final LocalDateTime veryFirstDateInDatabase = LocalDate.of(2020, 6, 14)
            .atTime(15, 0,0);
    private static final LocalDateTime lastMomentDateInDatabase = LocalDate.of(2020, 6, 14)
            .atTime(18, 30,0);

    @Autowired
    private JpaPriceRepository priceRepository;
    private JpaPrice jpaPrice;

    @BeforeEach
    public void setup() {
        jpaPrice = new JpaPrice(priceRepository);
        priceRepository.deleteAll();

        LocalDateTime startDate = LocalDate.of(2020, 6, 14)
                .atTime(0, 0, 0);
        LocalDateTime endDate = LocalDate.of(2020, 12, 31)
                .atTime(23, 59,59);
        PriceId priceId = new PriceId(ZARA_BRAND_ID, startDate, endDate, PRODUCT_ID, 0);
        PriceDataMapper priceDataMapper = new PriceDataMapper(priceId, 1, 35.50,
                CurrencyModel.EUR);
        priceRepository.save(priceDataMapper);

        LocalDateTime startDate2 = LocalDate.of(2020, 6, 14)
                .atTime(15, 0, 0);
        LocalDateTime endDate2 = LocalDate.of(2020, 6, 14)
                .atTime(18, 30,0);
        PriceId priceId2 = new PriceId(ZARA_BRAND_ID, startDate2, endDate2, PRODUCT_ID, 1);
        PriceDataMapper priceDataMapper2 = new PriceDataMapper(priceId2, 2, 25.45,
                CurrencyModel.EUR);
        priceRepository.save(priceDataMapper2);

        LocalDateTime startDate3 = LocalDate.of(2020, 6, 15)
                .atTime(0, 0, 0);
        LocalDateTime endDate3 = LocalDate.of(2020, 6, 15)
                .atTime(11, 0,0);
        PriceId priceId3 = new PriceId(ZARA_BRAND_ID, startDate3, endDate3, PRODUCT_ID, 1);
        PriceDataMapper priceDataMapper3 = new PriceDataMapper(priceId3, 3, 30.50,
                CurrencyModel.EUR);
        priceRepository.save(priceDataMapper3);

        LocalDateTime startDate4 = LocalDate.of(2020, 6, 15)
                .atTime(16, 0, 0);
        LocalDateTime endDate4 = LocalDate.of(2020, 12, 31)
                .atTime(23, 59,59);
        PriceId priceId4 = new PriceId(ZARA_BRAND_ID, startDate4, endDate4, PRODUCT_ID, 1);
        PriceDataMapper priceDataMapper4 = new PriceDataMapper(priceId4, 4, 38.95,
                CurrencyModel.EUR);
        priceRepository.save(priceDataMapper4);
    }

    @Test
    public void givenNoPrices_whenRequest_ThenReturnEmptyList() {
        priceRepository.deleteAll();
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(ZARA_BRAND_ID, PRODUCT_ID, dateNotInDatabase);
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).isEmpty();
    }

    @Test
    public void givenSetupPrices_whenRequestWithDateNotInRage_ThenReturnEmptyList() {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(ZARA_BRAND_ID, PRODUCT_ID, dateNotInDatabase);
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).isEmpty();
    }

    @Test
    public void givenSetupPrices_whenRequest_ThenReturnList() {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(ZARA_BRAND_ID, PRODUCT_ID, dateInDatabase);
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).isNotEmpty();
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).hasSize(2);
    }

    @Test
    public void givenSetupPrices_whenRequestSameMomentEndPrice_ThenIncludeThatPrice() {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(ZARA_BRAND_ID, PRODUCT_ID, veryFirstDateInDatabase);
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).isNotEmpty();
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).hasSize(2);
    }

    @Test
    public void givenSetupPrices_whenRequestSameMomentStartPrice_ThenIncludeThatPrice() {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(ZARA_BRAND_ID, PRODUCT_ID, lastMomentDateInDatabase);
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).isNotEmpty();
        assertThat(jpaPrice.getPrices(priceDsRequestModel)).hasSize(2);
    }


}
