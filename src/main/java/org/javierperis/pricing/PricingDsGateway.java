package org.javierperis.pricing;

import java.util.List;

interface PricingDsGateway {

    List<PriceDsResponseModel> getPrices(PriceDsRequestModel priceDsRequestModel);
}
