package com.angelfg.gadget_plus.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
//@Data // no se recomienda usarlo en entities por los proxies
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Slf4j
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 32, nullable = false)
    private String clientName;

    @Column(nullable = true)
    private LocalDateTime lastUpdated;

    // OneToOne por defecto es Fetch.EAGER y trae todo
    // Si el OneToOne contiene FetchType.LAZY se genera un error de tipo: LazyInitializationException
    // Si quiero utilizar el LAZY debo solo utilizar las variables que contiene este modelo y no el de la otra tabla
    // @ToString.Exclude // con el Lazy es para no llamar al ToString de la otra entity y salga el error
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bill", nullable = false, unique = true) // Es la union esta entity con la otra tabla
    private BillEntity bill;

    // Nombre del atributo de la relacion: private OrderEntity order; de ProductEntity
    @OneToMany(
        mappedBy = "order",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true // Desvinculo una orden de un producto, elimina el registro del producto
    )
    private Set<ProductEntity> products = new HashSet<>();

    public void addProduct(ProductEntity product) {
        this.products.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // Antes de que guarde puedo cargar datos
    @PrePersist
    public void prePersist() {
        this.setCreatedAt(LocalDateTime.now());
        log.info("Pre persist {}", this.getCreatedAt().toString());
    }

}
