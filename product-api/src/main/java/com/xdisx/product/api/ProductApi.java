package com.xdisx.product.api;

import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ProductApi {
    String ROOT_PATH = "/xdisx";

    @PostMapping(ROOT_PATH + "/product")
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponseDto createProduct(@Valid @RequestBody ProductCreateRequestDto productCreateRequest);

}
