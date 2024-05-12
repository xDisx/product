package com.xdisx.product.e2e.product.steps.context;

import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.e2e.common.context.ValidatedContext;
import feign.FeignException;
import lombok.Data;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Scope(SCOPE_CUCUMBER_GLUE)
@Data
public class GetProductsContext implements ValidatedContext {
    private int status;
    private FeignException exception;
    private List<ProductResponseDto> productResponseDto;

}
