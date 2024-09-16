package com.angelfg.gadget_plus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order") // Nombre de la relacion de la Base de datos, tal cual como se llama
    private OrderEntity order;

}
