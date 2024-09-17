package com.angelfg.gadget_plus.repositories;

import com.angelfg.gadget_plus.entities.RejectProductEntity;
import com.angelfg.gadget_plus.entities.RejectProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectProductRepository extends JpaRepository<RejectProductEntity, RejectProductId> {

}
