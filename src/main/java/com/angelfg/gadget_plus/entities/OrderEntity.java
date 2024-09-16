package com.angelfg.gadget_plus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 32, nullable = false)
    private String clientName;

    // OneToOne por defecto es Fetch.EAGER y trae todo
    // Si el OneToOne contiene FetchType.LAZY se genera un error de tipo: LazyInitializationException
    // Si quiero utilizar el LAZY debo solo utilizar las variables que contiene este modelo y no el de la otra tabla
    // @ToString.Exclude // con el Lazy es para no llamar al ToString de la otra entity y salga el error
    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "id_bill", nullable = false, unique = true) // Es la union esta entity con la otra tabla
    private BillEntity bill;

}
