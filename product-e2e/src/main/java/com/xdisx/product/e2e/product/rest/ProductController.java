package com.xdisx.product.e2e.product.rest;

import com.xdisx.product.api.ProductApi;
import com.xdisx.product.e2e.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        contextId = "productService",
        name = "${xdisx.service.product.client-name}",
        url = "${xdisx.service.product.url}",
        configuration = FeignConfig.class)
public interface ProductController extends ProductApi {}
