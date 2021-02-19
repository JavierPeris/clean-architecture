package org.javierperis.pricing.adapters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "prices")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class PriceDataMapper {

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
    private CurrencyModel currencyModel;

}

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
class PriceId implements Serializable  {

    @NotNull
    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;
}