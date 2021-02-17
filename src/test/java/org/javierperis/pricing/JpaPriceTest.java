package org.javierperis.pricing;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Transactional
public class JpaPriceTest {

    private static final Long ZARA_BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final LocalDateTime date = LocalDate.of(2020, 6, 14)
            .atTime(0, 0);

    @Resource
    private JpaPriceRepository priceRepository;
    private final JpaPrice jpaPrice = new JpaPrice(priceRepository);

    @Test
    public void givenNoPrices_whenRequest_ThenReturnEmptyList() {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(ZARA_BRAND_ID, PRODUCT_ID, date);
        jpaPrice.getPrices(priceDsRequestModel);
    }


}
