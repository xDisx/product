package com.xdisx.product.app.rest;

import com.xdisx.product.api.exception.ProductCreateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class ProductControllerExceptionHandlerTest {
  @InjectMocks
  private ProductControllerExceptionHandler classUnderTest;

  @Test
  void handleProductCreateException() {
    ResponseEntity<Object> responseEntity = classUnderTest.handleProductCreateException(mock(WebRequest.class), mock(ProductCreateException.class));
    assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
  }
}
