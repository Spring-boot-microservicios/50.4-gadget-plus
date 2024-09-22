package com.angelfg.gadget_plus.repositories;

import com.angelfg.gadget_plus.entities.ProductCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {

    Optional<ProductCatalogEntity> findByName(String name);

}
