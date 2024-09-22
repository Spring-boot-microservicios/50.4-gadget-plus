package com.angelfg.gadget_plus.controllers;

import com.angelfg.gadget_plus.entities.ProductCatalogEntity;
import com.angelfg.gadget_plus.enums.DateEval;
import com.angelfg.gadget_plus.enums.LikeKey;
import com.angelfg.gadget_plus.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "product-catalog")
@RequiredArgsConstructor
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;

    @GetMapping(path = "{id}")
    public ResponseEntity<ProductCatalogEntity> getById(@PathVariable String id) {
        return ResponseEntity.ok(this.productCatalogService.findById(UUID.fromString(id)));
    }

    @GetMapping(path = "name/{name}")
    public ResponseEntity<ProductCatalogEntity> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.productCatalogService.findByName(name));
    }

    @GetMapping(path = "like/{key}")
    public ResponseEntity<List<ProductCatalogEntity>> getByNameLike(@PathVariable LikeKey key, @RequestParam String word) {
        final String placeholder = "%";

        if (key.equals(LikeKey.AFTER)) {
            return ResponseEntity.ok(this.productCatalogService.findNameLike(placeholder + word));
        }

        if (key.equals(LikeKey.BEFORE)) {
            return ResponseEntity.ok(this.productCatalogService.findNameLike(word + placeholder));
        }

        if (key.equals(LikeKey.BETWEEN)) {
            return ResponseEntity.ok(this.productCatalogService.findNameLike(placeholder + word + placeholder));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "between")
    public ResponseEntity<List<ProductCatalogEntity>> getPriceBetween(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(this.productCatalogService.findPriceBetween(min, max));
    }

    @GetMapping(path = "category")
    public ResponseEntity<List<ProductCatalogEntity>> getByCategory(@RequestParam Long id) {
        return ResponseEntity.ok(this.productCatalogService.findByCategoryId(id));
    }

    @GetMapping(path = "date-launch/{key}")
    public ResponseEntity<List<ProductCatalogEntity>> getByDate(@PathVariable DateEval key, @RequestParam LocalDate date) {
        return ResponseEntity.ok(this.productCatalogService.findByLaunchingDate(date, key));
    }

    @GetMapping(path = "brand-rating-and")
    public ResponseEntity<List<ProductCatalogEntity>> getByBrandAndRatingAnd(@RequestParam String brand, @RequestParam Short rating) {
        return ResponseEntity.ok(this.productCatalogService.findByBrandAndRating(brand, rating));
    }

    @GetMapping(path = "brand-rating-or")
    public ResponseEntity<List<ProductCatalogEntity>> getByBrandAndRatingOr(@RequestParam String brand, @RequestParam Short rating) {
        return ResponseEntity.ok(this.productCatalogService.findByBrandOrRating(brand, rating));
    }

}
