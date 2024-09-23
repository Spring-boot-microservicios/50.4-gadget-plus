package com.angelfg.gadget_plus.repositories;

import com.angelfg.gadget_plus.entities.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    @Modifying // UPDATE, DELETE
    void deleteByClientName(String clientName);

    Boolean existsByClientName(String clientName);

}
