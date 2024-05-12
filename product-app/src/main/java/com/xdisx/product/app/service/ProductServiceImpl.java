package com.xdisx.product.app.service;

import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.api.exception.ProductCreateException;
import com.xdisx.product.app.repository.db.ProductRepository;
import com.xdisx.product.app.repository.db.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.xdisx.product.app.service.converter.ProductConverter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCT_SAVE_ERROR = "Unable to save product";
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        ProductEntity product = ProductConverter.fromCreateRequest(productCreateRequestDto);

        product = saveAndFlushProduct(product);
        return ProductConverter.toProductResponse(product);
    }

    private ProductEntity saveAndFlushProduct(ProductEntity product) {
        try {
            return productRepository.saveAndFlush(product);
        } catch (DataIntegrityViolationException e) {
            log.error("Unable to save product with values {}", product, e);
            throw new ProductCreateException(PRODUCT_SAVE_ERROR);
        }
    }
}
