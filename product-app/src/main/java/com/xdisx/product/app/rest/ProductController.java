package com.xdisx.product.app.rest;

import com.xdisx.product.api.ProductApi;
import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {
    private final ProductService productService;
    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequest) {
        log.info("Create product request: {}", productCreateRequest);
        return productService.createProduct(productCreateRequest);
    }
}
