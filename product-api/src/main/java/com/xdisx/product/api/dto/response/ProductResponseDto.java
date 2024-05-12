package com.xdisx.product.api.dto.response;

import com.xdisx.product.api.dto.DeviceTypeDto;
import com.xdisx.product.api.dto.DurationOptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private BigInteger ID;
    private String productName;
    private List<DeviceTypeDto> compatibility;
    private List<DurationOptionDto> durationOptions;
    private String description;
}
