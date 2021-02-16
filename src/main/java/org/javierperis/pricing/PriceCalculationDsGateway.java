package org.javierperis.pricing;

import java.util.List;

interface PriceCalculationDsGateway {

    List<PriceDsResponseModel> getPrices(PriceDsRequestModel priceDsRequestModel);
}
