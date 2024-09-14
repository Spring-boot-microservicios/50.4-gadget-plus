package com.angelfg.gadget_plus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "bill")
@Data
public class BillEntity {

    @Id
    @Column(nullable = false, length = 64)
    private String id;

    @Column
    private BigDecimal totalAmount;

    @Column(name = "client_rfc", length = 14, nullable = false)
    private String rfc;

    // Se genera la recursividad infinita error: StackOverflowError
    @ToString.Exclude // va a ignorar el order y evita la recursivdad infinita
    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // nombre de la propiedad quien esta mapeando
    private OrderEntity order;

}
