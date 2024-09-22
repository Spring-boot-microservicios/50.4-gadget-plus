package com.angelfg.gadget_plus.repositories;

import com.angelfg.gadget_plus.dtos.ReportProduct;
import com.angelfg.gadget_plus.entities.ProductCatalogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

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

    List<ProductCatalogEntity> findByBrandAndRatingGreaterThan(String brand, Short rating);

    List<ProductCatalogEntity> findByBrandOrRatingGreaterThan(String brand, Short rating);

    @Query("select new com.angelfg.gadget_plus.dtos.ReportProduct(" +
            "pc.brand, " +
            "avg(pc.price), " +
            "sum(pc.price)) " +
            "from productCatalog pc " +
            "group by pc.brand")
    List<ReportProduct> findAndMakeReport();

    Page<ProductCatalogEntity> findAllByBrand(String brand, Pageable pageable);

    // Llamar DB como => call count_total_products_by_brand('Lenovo', null);
    // value = "brand" =>  es el mismo nombre de entrada del storage procedure
    @Procedure(procedureName = "count_total_products_by_brand", outputParameterName = "response")
    Integer countTotalProductsByBrandStoreProcedure(@Param(value = "brand") String brand);

}
