package com.xdisx.product.app.service.converter;

import com.xdisx.product.api.dto.DeviceTypeDto;
import com.xdisx.product.api.dto.DurationOptionDto;
import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.app.repository.db.entity.DeviceType;
import com.xdisx.product.app.repository.db.entity.DurationOptionEntity;
import com.xdisx.product.app.repository.db.entity.ProductCompatibilityEntity;
import com.xdisx.product.app.repository.db.entity.ProductEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ProductConverter {
    public static ProductEntity fromCreateRequest(ProductCreateRequestDto productCreateRequestDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productCreateRequestDto.getProductName());
        productEntity.setDescription(productCreateRequestDto.getDescription());

        List<ProductCompatibilityEntity> compatibilities = productCreateRequestDto.getCompatibility().stream().map(c -> {
            ProductCompatibilityEntity cmpEntity = new ProductCompatibilityEntity();
            cmpEntity.setDeviceType(DeviceType.valueOf(c.toString()));
            // Avoid setting product here
            return cmpEntity;
        }).toList();

        List<DurationOptionEntity> durationOptions = productCreateRequestDto.getDurationOptions().stream().map(d -> {
            DurationOptionEntity durationOptionEntity = new DurationOptionEntity();
            durationOptionEntity.setYears(d.getYears());
            durationOptionEntity.setPrice(d.getPrice());
            // Avoid setting product here
            return durationOptionEntity;
        }).toList();

        // Set the relationships after the lists are fully created
        compatibilities.forEach(cmp -> cmp.setProduct(productEntity)); // Set the product once all are created
        durationOptions.forEach(dur -> dur.setProduct(productEntity)); // Set the product once all are created

        productEntity.setCompatibilities(compatibilities);
        productEntity.setDurationOptions(durationOptions);

        return productEntity;
    }

    public static ProductResponseDto toProductResponse(ProductEntity product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setID(product.getId());
        productResponseDto.setProductName(product.getProductName());
        productResponseDto.setCompatibility(product.getCompatibilities().stream().map(x -> DeviceTypeDto.valueOf(x.getDeviceType().toString())).toList());
        productResponseDto.setDurationOptions(product.getDurationOptions().stream().map(x -> new DurationOptionDto(x.getYears(), x.getPrice())).toList());
        productResponseDto.setDescription(product.getDescription());

        return productResponseDto;
    }
}
