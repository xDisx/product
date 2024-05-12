package com.xdisx.product.e2e.product.steps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.xdisx.product.e2e.common.utils.ReadFileUtil.readJson;
import static org.junit.jupiter.api.Assertions.fail;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestBuilderServiceProduct {
    private static final String JSON_READ_ERROR = "Failed to read the request JSON from %s";

    public ProductCreateRequestDto buildProductCreateRequest() {
        var filePath = "/json/product/ProductCreateRequest.json";
        ProductCreateRequestDto requestJson = null;

        try {
            requestJson = readJson(ProductCreateRequestDto.class, filePath);
        } catch (JsonProcessingException e) {
            fail(String.format(JSON_READ_ERROR, filePath));
        }

        return requestJson;
    }
}
