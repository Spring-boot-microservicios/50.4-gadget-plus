package com.angelfg.gadget_plus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity(name = "productCatalog")
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
    private String brand;

    private String description;
    private BigDecimal price;
    private LocalDate launchingDate;
    private Boolean isDiscount;
    private Short rating;


    // Muchos a muchos genera una tabla
    // No poder en CascadeType.ALL, no usar el CascadeType.REMOVE
    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = {
            CascadeType.PERSIST,
            // CascadeType.MERGE, // Conflicto con Multiple representations of the same entity are being merged o quitar todo el cascade
            CascadeType.DETACH,
            CascadeType.REFRESH
        }
    )
    @JoinTable(
        name = "product_join_category",
        joinColumns = @JoinColumn(name = "id_product"),
        inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private List<CategoryEntity> categories = new LinkedList<>();

    public void addCategory(CategoryEntity category) {
        this.categories.add(category);
    }

}
