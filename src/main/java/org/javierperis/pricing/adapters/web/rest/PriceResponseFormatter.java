package org.javierperis.pricing.adapters.web.rest;

import org.javierperis.pricing.usecases.PricingPresenter;
import org.javierperis.pricing.usecases.models.PriceResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PriceResponseFormatter implements PricingPresenter {

    @Override
    public PriceResponseModel prepareSuccessView(PriceResponseModel price) {
        return price;
    }

    @Override
    public PriceResponseModel prepareFailView(String error) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
    }
}
