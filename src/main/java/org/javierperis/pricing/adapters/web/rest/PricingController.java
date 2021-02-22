package org.javierperis.pricing.adapters.web.rest;

import org.javierperis.pricing.usecases.models.PriceRequestModel;
import org.javierperis.pricing.usecases.models.PriceResponseModel;
import org.javierperis.pricing.usecases.PricingInputBoundary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PricingController {

    final PricingInputBoundary priceInput;

    public PricingController(PricingInputBoundary priceInput) {
        this.priceInput = priceInput;
    }

    @GetMapping("/prices")
    PriceResponseModel create(@RequestParam Integer brandId, @RequestParam Long productId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        PriceRequestModel priceRequestModel = new PriceRequestModel(brandId, productId, date);
        return priceInput.getPrice(priceRequestModel);
    }

}
