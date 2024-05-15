package com.xdisx.product.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DurationOptionDto {
    private BigInteger years;
    private BigDecimal price;
    private BigInteger ID;
}
