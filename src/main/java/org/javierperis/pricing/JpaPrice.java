package org.javierperis.pricing;

import java.util.ArrayList;
import java.util.List;

public class JpaPrice implements PricingDsGateway {

    final JpaPriceRepository repository;

    public JpaPrice(JpaPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PriceDsResponseModel> getPrices(PriceDsRequestModel priceDsRequestModel) {
        return convertToPriceDsResponseModel(
                repository
                        .findByPriceId_BrandIdAndPriceId_ProductIdAndPriceId_StartDateLessThanEqualAndPriceId_EndDateGreaterThanEqual(
                        priceDsRequestModel.getBrandId(),
                        priceDsRequestModel.getProductId(),
                        priceDsRequestModel.getDate(),
                        priceDsRequestModel.getDate()
                )
        );
    }

    private List<PriceDsResponseModel> convertToPriceDsResponseModel(List<PriceDataMapper> prices) {
        List<PriceDsResponseModel> priceDsResponseModel = new ArrayList<>();
        for (PriceDataMapper price : prices) {
            final PriceId priceId = price.getPriceId();
            priceDsResponseModel.add(
                    new PriceDsResponseModel(priceId.getProductId(), priceId.getBrandId(), price.getPriceList(),
                            priceId.getStartDate(), priceId.getEndDate(), priceId.getPriority(), price.getPrice(),
                            price.getCurrencyModel().name())
            );
        }
        return priceDsResponseModel;
    }


}
