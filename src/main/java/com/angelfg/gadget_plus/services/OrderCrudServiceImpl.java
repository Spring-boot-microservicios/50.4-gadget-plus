package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.dtos.OrderDTO;
import com.angelfg.gadget_plus.dtos.ProductDTO;
import com.angelfg.gadget_plus.entities.OrderEntity;
import com.angelfg.gadget_plus.entities.ProductEntity;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
        return this.mapOrderFromEntity(this.orderRepository.findById(id).orElseThrow());
    }

    @Override
    public OrderDTO update(OrderDTO order, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private OrderDTO mapOrderFromEntity(OrderEntity orderEntity) {
        final ModelMapper modelMapper = new ModelMapper();

        // Cuando mape mi dto, lo va añadir manualmente al productDTO el name desde el ProductCatalog name
        modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
              .addMappings(mapper -> mapper.map(
                  entity -> entity.getCatalog().getName(), ProductDTO::setName
              ));

        return modelMapper.map(orderEntity, OrderDTO.class);
    }

}
