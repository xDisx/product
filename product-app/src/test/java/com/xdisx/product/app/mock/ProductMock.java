package com.xdisx.product.app.mock;

import com.xdisx.product.api.dto.DeviceTypeDto;
import com.xdisx.product.api.dto.DurationOptionDto;
import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.app.repository.db.entity.DeviceType;
import com.xdisx.product.app.repository.db.entity.DurationOptionEntity;
import com.xdisx.product.app.repository.db.entity.ProductCompatibilityEntity;
import com.xdisx.product.app.repository.db.entity.ProductEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@UtilityClass
public class ProductMock {
  private static final String PRODUCT_NAME = "Test product";
  private static final BigInteger ID = BigInteger.ONE;
  private static final String DESCRIPTION = "Test product description";
  private static final List<DeviceTypeDto> COMPATIBILITY =
      List.of(DeviceTypeDto.SMARTPHONE, DeviceTypeDto.LAPTOP);
  private static final List<DurationOptionDto> DURATION_OPTIONS = List.of(new DurationOptionDto(BigInteger.ONE, BigDecimal.ONE));

    public static ProductCreateRequestDto getCreateProductRequest() {
        return ProductCreateRequestDto.builder()
                .productName(PRODUCT_NAME)
                .description(DESCRIPTION)
                .compatibility(COMPATIBILITY)
                .durationOptions(DURATION_OPTIONS)
                .build();
    }

    public static ProductResponseDto getProductResponse() {
        return ProductResponseDto.builder()
                .ID(ID).build();
    }

    public static ProductEntity getProductEntity(ProductCreateRequestDto requestDto) {
      ProductEntity productEntity = new ProductEntity();
      productEntity.setId(ID);
      productEntity.setProductName(requestDto.getProductName());
      productEntity.setDescription(requestDto.getDescription());
    productEntity.setDurationOptions(
        requestDto.getDurationOptions().stream().map(x -> {
          var option = new DurationOptionEntity();
          option.setPrice(x.getPrice());
          option.setYears(x.getYears());
          return option;
        }).toList());
    productEntity.setCompatibilities(requestDto.getCompatibility().stream().map(x-> {
      var option = new ProductCompatibilityEntity();
      option.setDeviceType(DeviceType.valueOf(x.toString()));
      return option;

    }).toList());
    return productEntity;
    };
}
