package com.angelfg.gadget_plus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CodeCategoryEnum code;

    private String description;

    // Se mapea con la propiedad de private List<CategoryEntity> categories; de ProductCatalogEntity
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    @ToString.Exclude // Evitar recursividad infinita
    private List<ProductCatalogEntity> productsCatalog;

}
