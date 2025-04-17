package com.thanglv.broadleafstore.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmountAndCurrency implements Serializable {
    @NotNull(message = "amount can not be null")
    private BigDecimal amount;
    @NotNull(message = "currency can not be null")
    private String currency;
}
