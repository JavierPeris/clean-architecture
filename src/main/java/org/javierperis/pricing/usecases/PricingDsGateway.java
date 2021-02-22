package org.javierperis.pricing.usecases;

import org.javierperis.pricing.usecases.models.PriceDsRequestModel;
import org.javierperis.pricing.usecases.models.PriceDsResponseModel;

import java.util.List;

public interface PricingDsGateway {

    List<PriceDsResponseModel> getPrices(PriceDsRequestModel priceDsRequestModel);
}
