package org.javierperis.pricing;

public interface PriceInputBoundary {

    PriceResponseModel getPrice(PriceRequestModel priceRequestModel);
}
