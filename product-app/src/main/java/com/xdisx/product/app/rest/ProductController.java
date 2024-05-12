package com.xdisx.product.app.rest;

import com.xdisx.product.api.ProductApi;
import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

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

    @Override
    public List<ProductResponseDto> getProducts() {
        log.info("Get products request received");
        return productService.getProducts();
    }

    @Override
    public ProductResponseDto getProduct(BigInteger id) {
        return productService.getProduct(id);
    }
}
