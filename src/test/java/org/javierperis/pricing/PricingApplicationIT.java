package org.javierperis.pricing;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PricingApplicationIT {

    private static final Integer ZARA_BRAND_ID = 1;
    private static final Integer PRODUCT_ID = 35455;
    private static final Currency EURO_CURRENCY = Currency.getInstance("EUR");

    @Autowired
    private MockMvc restPricesMockMvc;

    //Request at 10:00 on June 14th, 2020 for product 35455 for brand 1 (ZARA)
    @Disabled("To be met")
    @Test
    @Transactional
    public void givenTestData_whenRequestAt10Of14thOfProduct354455ForBrand1_thenReturnRow1() throws Exception {
        final LocalDateTime requestLocalDateTime = LocalDate.of(2020, 6, 14)
                .atTime(10, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(0, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 12, 31)
                .atTime(23, 59);
        final int expectedPriceList = 1;
        final double expectedPrice = 35.50D;

        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", requestLocalDateTime.toString())
                .queryParam("product", "35455")
                .queryParam("brand", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 16:00 on June 14th, 2020 for product 35455 for brand 1 (ZARA)
    @Disabled("To be met")
    @Test
    @Transactional
    public void givenTestData_whenRequestAt16Of14thOfProduct354455ForBrand1_thenReturnRow2() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14)
                .atTime(16, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(15, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(18, 30);
        final int expectedPriceList = 2;
        final double expectedPrice = 25.45D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("product", "35455")
                .queryParam("brand", "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 21:00 on June 14th, 2020 for product 35455 for brand 1 (ZARA)
    @Disabled("To be met")
    @Test
    @Transactional
    public void givenTestData_whenRequestAt21Of14thOfProduct354455ForBrand1_thenReturnRow1() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14)
                .atTime(21, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(10, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(10, 0);
        final int expectedPriceList = 1;
        final double expectedPrice = 35.50D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("product", "35455")
                .queryParam("brand", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 10:00 on June 16th, 2020 for product 35455 for brand 1 (ZARA)
    @Disabled("To be met")
    @Test
    @Transactional
    public void givenTestData_whenRequestAt10Of15thOfProduct354455ForBrand1_thenReturnRow3() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 15)
                .atTime(10, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 15)
                .atTime(0, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 6, 15)
                .atTime(11, 0);
        final int expectedPriceList = 3;
        final double expectedPrice = 30.50D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("product", "35455")
                .queryParam("brand", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 21:00 on June 16th, 2020 for product 35455 for brand 1 (ZARA)
    @Disabled("To be met")
    @Test
    @Transactional
    public void givenTestData_whenRequestAt21Of16thOfProduct354455ForBrand1_thenReturnRow4() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 16)
                .atTime(21, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 15)
                .atTime(16, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 12, 31)
                .atTime(23, 59);
        final int expectedPriceList = 4;
        final double expectedPrice = 38.95D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("product", "35455")
                .queryParam("brand", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice.toString()))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }
}
