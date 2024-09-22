package com.angelfg.gadget_plus.repositories;

import com.angelfg.gadget_plus.entities.ProductCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {

    Optional<ProductCatalogEntity> findByName(String name);

    List<ProductCatalogEntity> findByNameLike(String key);

    @Query("from productCatalog p where p.price between :min and :max")
    List<ProductCatalogEntity> findByBetweenTwoPrices(BigDecimal min, BigDecimal max);

    @Query("from productCatalog p left join fetch p.categories c where c.id = :categoryId")
    List<ProductCatalogEntity> getByCategory(Long categoryId);

    List<ProductCatalogEntity> findByLaunchingDateBefore(LocalDate date);

    List<ProductCatalogEntity> findByLaunchingDateAfter(LocalDate date);

}
