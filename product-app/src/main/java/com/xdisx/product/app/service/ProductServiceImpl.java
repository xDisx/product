package com.xdisx.product.app.service;

import com.xdisx.product.api.dto.request.ProductCreateRequestDto;
import com.xdisx.product.api.dto.request.ProductsRequestDto;
import com.xdisx.product.api.dto.response.ProductResponseDto;
import com.xdisx.product.api.exception.ProductCreateException;
import com.xdisx.product.api.exception.ProductNotFoundException;
import com.xdisx.product.app.repository.db.ProductRepository;
import com.xdisx.product.app.repository.db.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.xdisx.product.app.service.converter.ProductConverter;
import com.xdisx.product.app.repository.db.filtering.ProductSpecificationBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCT_SAVE_ERROR = "Unable to save product";
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        ProductEntity product = ProductConverter.fromCreateRequest(productCreateRequestDto);

        product = saveAndFlushProduct(product);
        return ProductConverter.toProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProducts(ProductsRequestDto productsRequest) {
        Specification<ProductEntity> specifications =
                ProductSpecificationBuilder.getFilterSpecifications(productsRequest);

        Sort sort = Sort.by(Sort.Direction.DESC, "created");

        return productRepository.findAll(specifications, sort).stream().map(ProductConverter::toProductResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(BigInteger id) {
        ProductEntity product =
                productRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ProductNotFoundException(
                                                String.format("There is no product with id %d", id)));
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
