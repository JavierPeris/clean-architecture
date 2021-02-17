package org.javierperis.pricing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class JpaPriceIT {

    private static final Integer ZARA_BRAND_ID = 1;
    private static final Long PRODUCT_ID = 35455L;
    private static final LocalDateTime date = LocalDate.of(2020, 6, 14)
            .atTime(0, 0);

    @Autowired
    private JpaPriceRepository priceRepository;
    private JpaPrice jpaPrice;

    @Test
    public void givenNoPrices_whenRequest_ThenReturnEmptyList() {
        jpaPrice = new JpaPrice(priceRepository);
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(ZARA_BRAND_ID, PRODUCT_ID, date);
        jpaPrice.getPrices(priceDsRequestModel);
    }


}
