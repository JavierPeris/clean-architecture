package org.javierperis.pricing.adapters.web.rest;

import org.javierperis.pricing.adapters.web.rest.PriceResponseFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceResponseFormatterTest {

    PriceResponseFormatter priceResponseFormatter = new PriceResponseFormatter();

    @Test
    void whenPrepareFailView_thenThrowHttpConflictException() {
        assertThatThrownBy(() -> priceResponseFormatter
                .prepareFailView("There's no price for the product and date specified"))
                .isInstanceOf(ResponseStatusException.class);
    }

}