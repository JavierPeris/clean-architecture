package org.javierperis.pricing.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceDataMapper, PriceId> {


    List<PriceDataMapper>
            findByPriceId_BrandIdAndPriceId_ProductIdAndPriceId_StartDateLessThanEqualAndPriceId_EndDateGreaterThanEqual
            (Integer brandId, Long productId, LocalDateTime timeToCompareWithStart, LocalDateTime timeToCompareWithEnd);

}
