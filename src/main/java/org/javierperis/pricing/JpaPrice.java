package org.javierperis.pricing;

import java.util.ArrayList;
import java.util.List;

public class JpaPrice implements PriceCalculationDsGateway {

    final JpaPriceRepository repository;

    public JpaPrice(JpaPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PriceDsResponseModel> getPrices(PriceDsRequestModel priceDsRequestModel) {
        return new ArrayList<>();
    }

}
