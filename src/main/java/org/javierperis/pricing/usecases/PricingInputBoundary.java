package org.javierperis.pricing.usecases;

public interface PricingInputBoundary {

    PriceResponseModel getPrice(PriceRequestModel priceRequestModel);
}
