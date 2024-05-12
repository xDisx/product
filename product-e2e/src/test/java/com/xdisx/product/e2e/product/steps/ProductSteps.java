package com.xdisx.product.e2e.product.steps;

import com.xdisx.product.e2e.CucumberBootstrap;
import com.xdisx.product.e2e.common.utils.AssertionsUtils;
import com.xdisx.product.e2e.product.rest.ProductController;
import com.xdisx.product.e2e.product.steps.service.RequestBuilderServiceProduct;
import feign.FeignException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
public class ProductSteps extends CucumberBootstrap {
    private final ProductController productController;
    private final RequestBuilderServiceProduct requestBuilderServiceProduct;

    @Before
    public void setup() {
        productCreationContext.reset();
    }

    @When("I create a new product")
    public void iCreateANewProduct() {
        var createProductRequest = requestBuilderServiceProduct.buildProductCreateRequest();

        try {
            productCreationContext.setResponse(productController.createProduct(createProductRequest));
            productCreationContext.setStatus(OK.value());
        } catch (FeignException e) {
            productCreationContext.setStatus(e.status());
            productCreationContext.setException(e);
        }
    }

    @Then("I receive the created product")
    public void iReceiveTheCreatedProduct() {
        AssertionsUtils.assertAPISuccess(productCreationContext);

        var productResponse = productCreationContext.getResponse();
        assertNotNull(productResponse);
    }
}
