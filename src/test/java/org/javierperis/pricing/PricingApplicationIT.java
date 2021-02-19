package org.javierperis.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PricingApplicationIT {

    private static final Integer ZARA_BRAND_ID = 1;
    private static final Long PRODUCT_ID = 35455L;
    private static final Currency EURO_CURRENCY = Currency.getInstance("EUR");

    @Autowired
    private MockMvc restPricesMockMvc;

    @Autowired
    private JpaPriceRepository priceRepository;

    @BeforeEach
    public void setup() {
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

    //Request at 10:00 on June 14th, 2020 for product 35455 for brand 1 (ZARA)
    @Test
    @Transactional
    public void givenTestData_whenRequestAt10Of14thOfProduct354455ForBrand1_thenReturnRow1() throws Exception {
        final LocalDateTime requestLocalDateTime = LocalDate.of(2020, 6, 14)
                .atTime(10, 0, 0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(0, 0, 0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 12, 31)
                .atTime(23, 59, 59);
        final int expectedPriceList = 1;
        final double expectedPrice = 35.50D;

        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", requestLocalDateTime.toString())
                .queryParam("productId", "35455")
                .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 16:00 on June 14th, 2020 for product 35455 for brand 1 (ZARA)
    @Test
    @Transactional
    public void givenTestData_whenRequestAt16Of14thOfProduct354455ForBrand1_thenReturnRow2() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14)
                .atTime(16, 0,0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(15, 0,0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(18, 30,0);
        final int expectedPriceList = 2;
        final double expectedPrice = 25.45D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("productId", "35455")
                .queryParam("brandId", "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 21:00 on June 14th, 2020 for product 35455 for brand 1 (ZARA)
    @Test
    @Transactional
    public void givenTestData_whenRequestAt21Of14thOfProduct354455ForBrand1_thenReturnRow1() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 14)
                .atTime(21, 0,0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 14)
                .atTime(0, 0,0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 12, 31)
                .atTime(23, 59,59);
        final int expectedPriceList = 1;
        final double expectedPrice = 35.50D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("productId", "35455")
                .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 10:00 on June 16th, 2020 for product 35455 for brand 1 (ZARA)
    @Test
    @Transactional
    public void givenTestData_whenRequestAt10Of15thOfProduct354455ForBrand1_thenReturnRow3() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 15)
                .atTime(10, 0,0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 15)
                .atTime(0, 0,0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 6, 15)
                .atTime(11, 0,0);
        final int expectedPriceList = 3;
        final double expectedPrice = 30.50D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("productId", "35455")
                .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }

    //Request at 21:00 on June 16th, 2020 for product 35455 for brand 1 (ZARA)
    @Test
    @Transactional
    public void givenTestData_whenRequestAt21Of16thOfProduct354455ForBrand1_thenReturnRow4() throws Exception {
        final LocalDateTime localDateTime = LocalDate.of(2020, 6, 16)
                .atTime(21, 0,0);
        final LocalDateTime expectedStartDateForApplicablePrice = LocalDate.of(2020, 6, 15)
                .atTime(16, 0,0);
        final LocalDateTime expectedEndDateForApplicablePrice = LocalDate.of(2020, 12, 31)
                .atTime(23, 59,59);
        final int expectedPriceList = 4;
        final double expectedPrice = 38.95D;
        restPricesMockMvc.perform(get("/api/prices/")
                .queryParam("date", localDateTime.toString())
                .queryParam("productId", "35455")
                .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                .andExpect(jsonPath("$.brandId").value(ZARA_BRAND_ID))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.startDate").value(expectedStartDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.endDate").value(expectedEndDateForApplicablePrice
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(EURO_CURRENCY.toString()));
    }
}
