package com.xdisx.product.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.request.ProductsRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.app.mock.ProductMock;
import com.xdisx.product.app.service.ProductService;
import com.xdisx.product.app.util.FileReadUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
  private static final ObjectMapper mapper =
          new ObjectMapper()
                  .registerModule(new JavaTimeModule())
                  .setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
  private static final String PRODUCT_PATH = "/xdisx/product";
  private static final String PRODUCT_RESPONSE_JSON = "ProductResponse.json";
  private static final String PRODUCTS_PATH = "/xdisx/products";
  private static final String PRODUCTS_RESPONSE_JSON = "ProductsResponse.json";

  @Mock
  private ProductService productService;
  @InjectMocks
  private ProductController classUnderTest;
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
            new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.setObjectMapper(mapper);

    mockMvc =
            MockMvcBuilders.standaloneSetup(classUnderTest)
                    .setMessageConverters(mappingJackson2HttpMessageConverter)
                    .setControllerAdvice(ProductControllerExceptionHandler.class)
                    .build();
  }

  @Test
  void createProduct() throws Exception {
    ProductCreateRequestDto productCreateRequestDto = ProductMock.getCreateProductRequest();
    ProductResponseDto response = ProductMock.getProductResponse();

    when(productService.createProduct(productCreateRequestDto)).thenReturn(response);

    var apiResponse =
            mockMvc
                    .perform(
                            post(PRODUCT_PATH)
                                    .content(mapper.writeValueAsString(productCreateRequestDto))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

    var expectedResponse = FileReadUtil.readResourceAsString(PRODUCT_RESPONSE_JSON);
    JSONAssert.assertEquals(expectedResponse, apiResponse, JSONCompareMode.LENIENT);
  }

  @Test
  void getProducts() throws Exception {
    List<ProductResponseDto> responseDtos = ProductMock.getProductsResponse();
    when(productService.getProducts(ProductsRequestDto.builder().build())).thenReturn(responseDtos);

    var apiResponse =
            mockMvc
                    .perform(
                            get(PRODUCTS_PATH)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

    assertNotNull(apiResponse);
    var expectedResponse = FileReadUtil.readResourceAsString(PRODUCTS_RESPONSE_JSON);
    JSONAssert.assertEquals(expectedResponse, apiResponse, JSONCompareMode.LENIENT);
    }

  @Test
  void getProduct() throws Exception {
    ProductResponseDto responseDto = ProductMock.getProductResponse();
    when(productService.getProduct(responseDto.getID())).thenReturn(responseDto);

    var apiResponse =
            mockMvc
                    .perform(
                            get(PRODUCTS_PATH + "/" + responseDto.getID())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

    assertNotNull(apiResponse);
    var expectedResponse = FileReadUtil.readResourceAsString(PRODUCT_RESPONSE_JSON);
    JSONAssert.assertEquals(expectedResponse, apiResponse, JSONCompareMode.LENIENT);
    }
}
