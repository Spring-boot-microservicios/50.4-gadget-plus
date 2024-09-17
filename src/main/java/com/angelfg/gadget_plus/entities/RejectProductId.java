package com.angelfg.gadget_plus.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Llave compuesta
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RejectProductId {
    private String productName;
    private String brandName;
}
