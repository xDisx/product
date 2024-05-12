package com.xdisx.product.app.service;

import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.api.exception.ProductCreateException;
import com.xdisx.product.app.mock.ProductMock;
import com.xdisx.product.app.repository.db.ProductRepository;
import com.xdisx.product.app.repository.db.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static com.xdisx.product.app.service.ProductServiceImpl.PRODUCT_SAVE_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl classUnderTest;

  @Test
  void createProduct() {
    ProductCreateRequestDto requestDto = ProductMock.getCreateProductRequest();
    ProductEntity product = ProductMock.getProductEntity(requestDto);
    ProductEntity product2 = ProductMock.getProductEntity(requestDto);
    assertEquals(product, product2);

    when(productRepository.saveAndFlush(
            argThat(arg -> requestDto.getProductName().equals(arg.getProductName()))))
            .thenReturn(product);
    var savedProduct = classUnderTest.createProduct(requestDto);

    verify(productRepository)
            .saveAndFlush(
                    argThat(
                            arg -> {
                              assertEquals(requestDto.getProductName(), arg.getProductName());
                              return true;
                            }));

    assertNotNull(savedProduct);
    assertProductResponseWithRequest(requestDto, savedProduct);
  }

  private void assertProductResponseWithRequest(ProductCreateRequestDto requestDto, ProductResponseDto savedProduct) {

  }

  @Test
  void createContract_throwsExceptionWhenDataIntegrityIsViolated() {
    ProductCreateRequestDto requestDto = ProductMock.getCreateProductRequest();

    doThrow(new DataIntegrityViolationException("Database error"))
            .when(productRepository)
            .saveAndFlush(any(ProductEntity.class));

    ProductCreateException thrown =
            assertThrows(
                    ProductCreateException.class,
                    () -> {
                      classUnderTest.createProduct(requestDto);
                    },
                    "Expected createCustomer to throw, but it didn't");

    assertEquals(PRODUCT_SAVE_ERROR, thrown.getMessage(), "Exception message does not match");
  }
}
