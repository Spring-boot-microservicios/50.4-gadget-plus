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

}
