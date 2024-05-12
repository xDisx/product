package com.xdisx.product.app.repository.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "product_compatibility")
@Getter
@Setter
public class ProductCompatibilityEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compatibility_id_gen")
    @SequenceGenerator(name = "compatibility_id_gen", sequenceName = "compatibility_id_seq", allocationSize = 1)
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @NotNull
    @Column(name = "device_type", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

}
