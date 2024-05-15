package com.xdisx.product.app.repository.db;

import com.xdisx.product.app.repository.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, BigInteger>, JpaSpecificationExecutor<ProductEntity> {

}
