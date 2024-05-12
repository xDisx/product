package com.xdisx.product.app.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
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

import java.net.URI;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
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
}
