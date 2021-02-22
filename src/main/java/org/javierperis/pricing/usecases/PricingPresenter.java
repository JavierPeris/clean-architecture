package org.javierperis.pricing.usecases;

import org.javierperis.pricing.usecases.models.PriceResponseModel;

public interface PricingPresenter {

    PriceResponseModel prepareSuccessView(PriceResponseModel priceResponseModel);

    PriceResponseModel prepareFailView(String error);
}
