package org.javierperis.pricing.usecases;

public interface PricingPresenter {

    PriceResponseModel prepareSuccessView(PriceResponseModel priceResponseModel);

    PriceResponseModel prepareFailView(String error);
}
