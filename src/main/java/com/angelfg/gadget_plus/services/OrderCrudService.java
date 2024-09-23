package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.dtos.OrderDTO;

public interface OrderCrudService {
    String create(OrderDTO order);
    OrderDTO read(Long id);
    OrderDTO update(OrderDTO order, Long id);
    void delete(Long id);
}
