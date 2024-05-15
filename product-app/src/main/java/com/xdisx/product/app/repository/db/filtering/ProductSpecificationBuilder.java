package com.xdisx.product.app.repository.db.filtering;

import com.xdisx.product.api.dto.DeviceTypeDto;
import com.xdisx.product.api.dto.request.ProductsRequestDto;
import com.xdisx.product.app.repository.db.entity.ProductCompatibilityEntity;
import com.xdisx.product.app.repository.db.entity.ProductEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ProductSpecificationBuilder {
    public static Specification<ProductEntity> getFilterSpecifications(ProductsRequestDto productsRequest) {
        List<Specification<ProductEntity>> specifications = new ArrayList<>();

        if (productsRequest.getDeviceType() != null) {
            specifications.add(buildDeviceTypeExactSpecification(productsRequest.getDeviceType()));
        }

        return Specification.allOf(specifications);
    }

    private static Specification<ProductEntity> buildDeviceTypeExactSpecification(DeviceTypeDto deviceType) {
        return (root, query, criteriaBuilder) -> {
            Join<ProductEntity, ProductCompatibilityEntity> join = root.join("compatibilities", JoinType.INNER);
            return criteriaBuilder.equal(join.get("deviceType"), deviceType);
        };
    }
}
