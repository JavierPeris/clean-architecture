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
