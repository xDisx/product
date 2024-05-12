package com.xdisx.product.api;

import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

public interface ProductApi {
    String ROOT_PATH = "/xdisx";

    @PostMapping(ROOT_PATH + "/product")
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponseDto createProduct(@Valid @RequestBody ProductCreateRequestDto productCreateRequest);

    @GetMapping(ROOT_PATH+"/products")
    @ResponseStatus(HttpStatus.OK)
    List<ProductResponseDto> getProducts();

    @GetMapping(ROOT_PATH+"/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductResponseDto getProduct(@PathVariable("id") @NotNull BigInteger id);

}
