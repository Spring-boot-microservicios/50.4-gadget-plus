package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.dtos.OrderDTO;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCrudServiceImpl implements OrderCrudService {

    private final OrderRepository orderRepository;

    @Override
    public String create(OrderDTO order) {
        return "";
    }

    @Override
    public OrderDTO read(Long id) {
        return null;
    }

    @Override
    public OrderDTO update(OrderDTO order, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}
