package com.xdisx.product.app.service;

import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.request.ProductsRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto);

    List<ProductResponseDto> getProducts(ProductsRequestDto productsRequest);

    ProductResponseDto getProduct(BigInteger id);
}
