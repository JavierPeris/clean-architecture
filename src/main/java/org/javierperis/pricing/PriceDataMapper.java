package org.javierperis.pricing;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "prices")
@Entity
public class PriceDataMapper {

    enum Currency {
        EUR, USD, GBP
    }

    @EmbeddedId PriceId priceId;
    @NotNull
    @Column(name = "price_list", nullable = false)
    private Integer priceList;
    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Double price;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "curr", nullable = false)
    private Currency currency;

}

@Embeddable
class PriceId implements Serializable  {

    @NotNull
    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;
}