package com.thanglv.broadleafstore.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmountAndCurrency implements Serializable {
    private BigDecimal amount;
    private String currency;
}
