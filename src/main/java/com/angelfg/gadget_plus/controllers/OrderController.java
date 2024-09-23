package com.angelfg.gadget_plus.controllers;

import com.angelfg.gadget_plus.dtos.OrderDTO;
import com.angelfg.gadget_plus.services.OrderCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCrudService orderCrudService;

    @GetMapping(path = "{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.orderCrudService.read(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDTO orderDTO) {
        String path = "/" + this.orderCrudService.create(orderDTO);
        return ResponseEntity.created(URI.create(path)).build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(this.orderCrudService.update(orderDTO, id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestParam String name) {
        this.orderCrudService.delete(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.orderCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
