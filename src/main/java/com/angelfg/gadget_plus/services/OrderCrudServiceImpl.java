package com.angelfg.gadget_plus.services;

import com.angelfg.gadget_plus.dtos.BillDTO;
import com.angelfg.gadget_plus.dtos.OrderDTO;
import com.angelfg.gadget_plus.dtos.ProductDTO;
import com.angelfg.gadget_plus.entities.BillEntity;
import com.angelfg.gadget_plus.entities.OrderEntity;
import com.angelfg.gadget_plus.entities.ProductCatalogEntity;
import com.angelfg.gadget_plus.entities.ProductEntity;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import com.angelfg.gadget_plus.repositories.ProductCatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCrudServiceImpl implements OrderCrudService {

    private final OrderRepository orderRepository;
    private final ProductCatalogRepository productCatalogRepository;

    @Override
    public String create(OrderDTO order) {
        final OrderEntity toInsert = this.mapOrderFromDto(order);
        return this.orderRepository.save(toInsert).getId().toString();
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

    private OrderEntity mapOrderFromDto(OrderDTO orderDTO) {

        final OrderEntity orderResponse = new OrderEntity();
        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(BillDTO.class, BillEntity.class)
            .addMappings(mapper -> mapper.map(
                BillDTO::getIdBill, BillEntity::setId
            ));

        log.info("Before {}", orderResponse);
        modelMapper.map(orderDTO, orderResponse);
        log.info("After {}", orderResponse);

        BigDecimal total = this.getAndSetProductsAndTotal(orderDTO.getProducts(), orderResponse);
        log.info("total {}", total);

        orderResponse.getBill().setTotalAmount(total);

        return orderResponse;
    }

    private BigDecimal getAndSetProductsAndTotal(List<ProductDTO> products, OrderEntity orderEntity) {

        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

        products.forEach(product -> {
            final ProductCatalogEntity productsFromCatalog = this.productCatalogRepository.findByName(product.getName()).orElseThrow();

            total.updateAndGet(bigDecimal -> bigDecimal.add(productsFromCatalog.getPrice()));

            final ProductEntity productEntity = ProductEntity.builder()
                    .quantity(product.getQuantity())
                    .catalog(productsFromCatalog)
                    .build();

            orderEntity.addProduct(productEntity);

            productEntity.setOrder(orderEntity);
        });

        return total.get();
    }

}
