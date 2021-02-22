package org.javierperis.pricing.usecases;

import org.javierperis.pricing.usecases.models.PriceRequestModel;
import org.javierperis.pricing.usecases.models.PriceResponseModel;

public interface PricingInputBoundary {

    PriceResponseModel getPrice(PriceRequestModel priceRequestModel);
}
