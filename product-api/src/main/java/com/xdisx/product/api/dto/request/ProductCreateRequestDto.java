package com.xdisx.product.api.dto.request;

import com.xdisx.product.api.dto.DeviceTypeDto;
import com.xdisx.product.api.dto.DurationOptionDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {
    @NotBlank(message = "Product name must not be blank!")
    private String productName;

    @NotNull(message = "Description must not be null!")
    private String description;

    @NotEmpty
    List<DeviceTypeDto> compatibility;

    @NotEmpty
    List<DurationOptionDto> durationOptions;
}
