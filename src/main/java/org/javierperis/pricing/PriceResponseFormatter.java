package org.javierperis.pricing;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PriceResponseFormatter implements PricePresenter {

    @Override
    public PriceResponseModel prepareSuccessView(PriceResponseModel price) {
        return price;
    }

    @Override
    public PriceResponseModel prepareFailView(String error) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
    }
}
