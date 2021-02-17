package org.javierperis.pricing;

import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class PriceCalculationInteractor {

    final PriceCalculationDsGateway priceCalculationDsGateway;
    final PricePresenter pricePresenter;

    public PriceCalculationInteractor(PriceCalculationDsGateway priceCalculationDsGateway, PricePresenter pricePresenter) {
        this.priceCalculationDsGateway = priceCalculationDsGateway;
        this.pricePresenter = pricePresenter;
    }

    public PriceResponseModel getPrice(PriceRequestModel priceRequestModel) {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(priceRequestModel.getBrandId(),
                priceRequestModel.getProductId(), priceRequestModel.getDate());
        List<PriceDsResponseModel> priceDsResponseModel = priceCalculationDsGateway.getPrices(priceDsRequestModel);

        final Optional<PriceDsResponseModel> optionalPrice = priceDsResponseModel.stream()
                .max(Comparator.naturalOrder());
        if (optionalPrice.isPresent()) {
            final PriceDsResponseModel price = optionalPrice.get();
            final PriceResponseModel priceResponseModel = new PriceResponseModel(price.getProductId(),
                    price.getBrandId(), price.getPriceList(), price.getStartDate(), price.getEndDate(),
                    price.getPrice(), Currency.getInstance(price.getCurrency()));
            return pricePresenter.prepareSuccessView(priceResponseModel);
        }
        return pricePresenter.prepareFailView("There's no price for the product and date specified");
    }
}
