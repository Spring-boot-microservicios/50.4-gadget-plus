package com.angelfg.gadget_plus.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "reject_products")
@IdClass(RejectProductId.class) // Para llave compuesta
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RejectProductEntity {

    @Id
    private String productName;

    @Id
    private String brandName;

    private Integer quantity;

}
