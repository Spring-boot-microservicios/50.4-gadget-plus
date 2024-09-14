package com.angelfg.gadget_plus.repositories;

import com.angelfg.gadget_plus.entities.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

}
