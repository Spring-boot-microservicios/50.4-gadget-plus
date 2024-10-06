package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.entities.BillEntity;
import com.angelfg.gadget_plus.entities.OrderEntity;
import com.angelfg.gadget_plus.repositories.BillRepository;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional(noRollbackFor = IllegalStateException.class)
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final OrderRepository orderRepository;
    private final BillRepository billRepository;

    @Override
    public void executeTransaction(Long id) {
        this.updateOrder(id);
    }

    @Override
    public void updateOrder(Long id) {
        final OrderEntity order = this.orderRepository.findById(id).orElseThrow();
        order.setCreatedAt(LocalDateTime.now());
        this.orderRepository.save(order);

        this.validProducts(id);
        this.updateBill(order.getBill().getId());
    }

    @Override
    public void updateBill(String id) {
        final BillEntity bill = this.billRepository.findById(id).orElseThrow();
        bill.setClientRfc("RB1234");
        this.billRepository.save(bill);
    }

    @Override
    public void validProducts(Long id) {
        final OrderEntity order = this.orderRepository.findById(id).orElseThrow();
        if (order.getProducts().isEmpty()) {
            throw new IllegalStateException("There are no products in the order");
        }
    }

}

/**
 * Transactional -> Hace rollback y no guarda ninguna transaccion en DB si hay una exception
 *
 * Transactional(noRollbackFor = IllegalStateException.class) -> Omite el rollback en una excepcion concreta
 * Osea que si una una insercion antes se afectara la DB y en seguida hay una excepcion no continuara pero lo primero
 * si guardara en DB
 *
 *
 *
 *
 */