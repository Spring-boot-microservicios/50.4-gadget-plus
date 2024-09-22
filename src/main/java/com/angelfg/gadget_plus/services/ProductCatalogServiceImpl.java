package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.entities.ProductCatalogEntity;
import com.angelfg.gadget_plus.repositories.ProductCatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor// (onConstructor = @__(@Autowired)) no es necesario
@Transactional(readOnly = true)
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogRepository productCatalogRepository;

    @Override
    public ProductCatalogEntity findById(UUID id) {
        return this.productCatalogRepository.findById(id).orElseThrow();
    }

    @Override
    public ProductCatalogEntity findByName(String name) {
        return this.productCatalogRepository.findByName(name).orElseThrow();
    }

    @Override
    public List<ProductCatalogEntity> findNameLike(String key) {
        return this.productCatalogRepository.findByNameLike(key);
    }

    @Override
    public List<ProductCatalogEntity> findNameBetween(BigDecimal min, BigDecimal max) {
        return List.of();
    }

    @Override
    public List<ProductCatalogEntity> findByCategoryId(Long id) {
        return List.of();
    }

    @Override
    public List<ProductCatalogEntity> findByBrandAndRating(String brand, Short rating) {
        return List.of();
    }

    @Override
    public Page<ProductCatalogEntity> findAll(String field, Boolean desc, Integer page) {
        return null;
    }

    @Override
    public Page<ProductCatalogEntity> findAllByBrand(String brand) {
        return null;
    }

    @Override
    public Integer countByBrand(String brand) {
        return 0;
    }

}
