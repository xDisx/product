package com.xdisx.product.api.dto.request;

import com.xdisx.product.api.dto.DeviceTypeDto;
import com.xdisx.product.api.dto.OrderByDirection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsRequestDto {

    @NotNull(message = "Sort by must not be null!")
    @Builder.Default
    private String sortBy = "created";

    @NotNull(message = "Order by must not be null!")
    @Builder.Default
    private OrderByDirection orderBy = OrderByDirection.DESC;

    private DeviceTypeDto deviceType;
}
