package org.javierperis.pricing.usecases;

import java.util.List;

public interface PricingDsGateway {

    List<PriceDsResponseModel> getPrices(PriceDsRequestModel priceDsRequestModel);
}
