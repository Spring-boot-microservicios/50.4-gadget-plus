package com.angelfg.gadget_plus.services;

public interface TransactionService {
    void executeTransaction(Long id);
    void updateOrder(Long id);
    void updateBill(String id);
    void validProducts(Long id);
}
