package org.javierperis.pricing;

public interface PricePresenter {

    PriceResponseModel prepareSuccessView(PriceResponseModel priceResponseModel);

    PriceResponseModel prepareFailView(String error);
}
