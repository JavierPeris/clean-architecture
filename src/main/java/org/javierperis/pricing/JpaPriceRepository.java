package org.javierperis.pricing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceDataMapper, PriceId> {

    List<PriceDataMapper> findByPriceId_BrandIdAndPriceId_ProductId(Integer brandId, Long productId);
}
