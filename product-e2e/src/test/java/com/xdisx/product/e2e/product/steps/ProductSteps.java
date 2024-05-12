package com.xdisx.product.e2e.product.steps;

import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.e2e.CucumberBootstrap;
import com.xdisx.product.e2e.common.utils.AssertionsUtils;
import com.xdisx.product.e2e.product.rest.ProductController;
import com.xdisx.product.e2e.product.steps.context.GetProductContext;
import com.xdisx.product.e2e.product.steps.context.GetProductsContext;
import com.xdisx.product.e2e.product.steps.service.RequestBuilderServiceProduct;
import feign.FeignException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
public class ProductSteps extends CucumberBootstrap {
    private final ProductController productController;
    private final RequestBuilderServiceProduct requestBuilderServiceProduct;
    private GetProductContext getProductContext;
    private GetProductsContext getProductsContext;

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

    @Given("a product exists in the system")
    public void aProductExistsInTheSystem() {
        productCreationContext.setResponse(fetchOrCreateAProduct());
    }

    private ProductResponseDto fetchOrCreateAProduct() {
        var products = productController.getProducts();
        return products.isEmpty() ? createProduct() : products.get(0);
    }

    private ProductResponseDto createProduct() {
        var createProductRequest = requestBuilderServiceProduct.buildProductCreateRequest();
        return productController.createProduct(createProductRequest);
    }

    @When("I request the details of the product")
    public void iRequestTheDetailsOfTheProduct() {
        getProductContext = new GetProductContext();
        try{
            getProductContext.setProductResponseDto(productController.getProduct(productCreationContext.getResponse().getID()));
            getProductContext.setStatus(OK.value());
        } catch (FeignException e) {
            getProductContext.setException(e);
        }

    }

    @Then("I receive the product")
    public void iReceiveTheProduct() {
        AssertionsUtils.assertAPISuccess(getProductContext);
        var product = getProductContext.getProductResponseDto();
        assertNotNull(product);
    }

    @When("I request the available products")
    public void iRequestTheAvailableProducts() {
        getProductsContext = new GetProductsContext();
        try{
            getProductsContext.setProductResponseDto(productController.getProducts());
            getProductsContext.setStatus(OK.value());
        }catch (FeignException e) {
            getProductsContext.setException(e);
        }

    }

    @Then("I receive the available products")
    public void iReceiveTheAvailableProducts() {
        AssertionsUtils.assertAPISuccess(getProductsContext);
        var products = getProductsContext.getProductResponseDto();
        assertNotNull(products);
    }
}
