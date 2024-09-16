package com.angelfg.gadget_plus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "products_catalog")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductCatalogEntity {

    // Ayuda para mapear mejor la entity
    // SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, IS_NULLABLE, COLUMN_DEFAULT
    //	FROM INFORMATION_SCHEMA.COLUMNS
    //	WHERE TABLE_NAME = 'products_catalog';

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name", length = 64)
    private String name;

    @Column(name = "brand_name", length = 64)
    private String brad;

    private String description;
    private BigDecimal price;
    private LocalDate launchingDate;
    private Boolean isDiscount;
    private Short rating;

}
