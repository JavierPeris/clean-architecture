package org.javierperis.pricing.usecases;

import org.javierperis.pricing.usecases.models.PriceDsRequestModel;
import org.javierperis.pricing.usecases.models.PriceDsResponseModel;
import org.javierperis.pricing.usecases.models.PriceRequestModel;
import org.javierperis.pricing.usecases.models.PriceResponseModel;

import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class PricingInteractor implements PricingInputBoundary {

    final PricingDsGateway pricingDsGateway;
    final PricingPresenter pricingPresenter;

    public PricingInteractor(PricingDsGateway pricingDsGateway, PricingPresenter pricingPresenter) {
        this.pricingDsGateway = pricingDsGateway;
        this.pricingPresenter = pricingPresenter;
    }

    public PriceResponseModel getPrice(PriceRequestModel priceRequestModel) {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(priceRequestModel.getBrandId(),
                priceRequestModel.getProductId(), priceRequestModel.getDate());
        List<PriceDsResponseModel> priceDsResponseModel = pricingDsGateway.getPrices(priceDsRequestModel);

        final Optional<PriceDsResponseModel> optionalPrice = priceDsResponseModel.stream()
                .max(Comparator.naturalOrder());
        if (optionalPrice.isPresent()) {
            final PriceDsResponseModel price = optionalPrice.get();
            final PriceResponseModel priceResponseModel = new PriceResponseModel(price.getProductId(),
                    price.getBrandId(), price.getPriceList(), price.getStartDate(), price.getEndDate(),
                    price.getPrice(), Currency.getInstance(price.getCurrency()));
            return pricingPresenter.prepareSuccessView(priceResponseModel);
        }
        return pricingPresenter.prepareFailView("There's no price for the product and date specified");
    }
}
