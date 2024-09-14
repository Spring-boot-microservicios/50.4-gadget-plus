package com.angelfg.gadget_plus.repositories;

import com.angelfg.gadget_plus.entities.BillEntity;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<BillEntity, String> {
}
