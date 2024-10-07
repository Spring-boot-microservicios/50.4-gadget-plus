package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.entities.BillEntity;
import com.angelfg.gadget_plus.entities.OrderEntity;
import com.angelfg.gadget_plus.repositories.BillRepository;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional(noRollbackFor = IllegalStateException.class)
//@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final OrderRepository orderRepository;
    private final BillRepository billRepository;

    @Transactional
    @Override
    public void executeTransaction(Long id) {
        log.info("TRANSACTION ACTIVE 1: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 1: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        this.updateOrder(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrder(Long id) {
        log.info("TRANSACTION ACTIVE 2: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 2: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final OrderEntity order = this.orderRepository.findById(id).orElseThrow();
        order.setCreatedAt(LocalDateTime.now());
        this.orderRepository.save(order);

        this.validProducts(id);
        this.updateBill(order.getBill().getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateBill(String id) {
        log.info("TRANSACTION ACTIVE 4: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 4: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final BillEntity bill = this.billRepository.findById(id).orElseThrow();
        bill.setClientRfc("RB1234");
        this.billRepository.save(bill);
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public void validProducts(Long id) {
        log.info("TRANSACTION ACTIVE 3: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 3: {}", TransactionSynchronizationManager.getCurrentTransactionName());
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
 * Transactional en metodos es mas importante que la que esta a nivel de clase, lo sobreescribe
 *
 * Transactional(propagation = Propagation.REQUIRED) -> es el default, y trabaja en unica transaccion todos los metodos
 *
 * Transactional(propagation = Propagation.NESTED) -> SubTransaccion dentro de la transaccion principal (no hay cambios en comportamientos)
 *
 *
 */