package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.entities.BillEntity;
import com.angelfg.gadget_plus.entities.OrderEntity;
import com.angelfg.gadget_plus.repositories.BillRepository;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional(noRollbackFor = IllegalStateException.class)
//@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final OrderRepository orderRepository;
    private final BillRepository billRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void executeTransaction(Long id) {
        log.info("TRANSACTION ACTIVE 1: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 1: {}", TransactionSynchronizationManager.getCurrentTransactionName());

//        try {
        this.updateOrder(id);
//        } catch (Exception e) {}
//
//        this.updateBill("b-3");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

        Random random = new Random();
        int randomNumber = 1000000 + random.nextInt(9000000);
        log.info("randomNumber: {}", randomNumber);
        bill.setClientRfc(String.valueOf(randomNumber));
        this.billRepository.save(bill);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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
 * Transactional(propagation = Propagation.REQUIRES_NEW) -> Se va a generar en transacciones diferentes
 *
 * Transactional(propagation = Propagation.NOT_SUPPORTED) -> Transaccion activa pero suspendida porque no genera niguna
 * modificacion a la DB (Aunque no este soportado, aun asi si falla genera un rollback)
 *
 * Transactional(isolation = Isolation.DEFAULT) -> no importa si se modifica o no se modifica
 * Transactional(isolation = Isolation.READ_UNCOMMITTED) -> Lee antes de que se haga el commit, son transacciones sucias
 * Transactional(isolation = Isolation.READ_COMMITTED) -> No me va a permitir leer hasta que se hayan hecho los commits en DB para evitar lectura sucia
 * Transactional(isolation = Isolation.REPEATABLE_READ) -> No se basan en un commit, y siempre en la transaccion tengan el mismo valor
 * Transactional(isolation = Isolation.SERIALIZABLE) -> Mucha consistencia en los datos en DB mejor que READ_COMMITTED y REPEATABLE_READ
 *
 */