package org.javierperis.pricing;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class PriceCalculationInteractor {

    final PriceCalculationDsGateway priceCalculationDsGateway;

    public PriceCalculationInteractor(PriceCalculationDsGateway priceCalculationDsGateway) {
        this.priceCalculationDsGateway = priceCalculationDsGateway;
    }

    public PriceResponseModel getPrice(PriceRequestModel priceRequestModel) {
        PriceDsRequestModel priceDsRequestModel = new PriceDsRequestModel(priceRequestModel.getBrandId(),
                priceRequestModel.getProductId(), priceRequestModel.getDate());
        List<PriceDsResponseModel> priceDsResponseModel = priceCalculationDsGateway.getPrices(priceDsRequestModel);

        final Optional<PriceDsResponseModel> optionalPrice = priceDsResponseModel.stream()
                .max(Comparator.naturalOrder());
        if (optionalPrice.isPresent()) {
            final PriceDsResponseModel price = optionalPrice.get();
            return new PriceResponseModel(price.getProductId(), price.getBrandId(), price.getPriceList(),
                    price.getStartDate(), price.getEndDate(), price.getPrice(),
                    Currency.getInstance(price.getCurrency()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
