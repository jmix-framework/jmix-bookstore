package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierOrderRepository implements EntityRepository<SupplierOrderData, SupplierOrder> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    SupplierOrderMapper mapper;

    @Override
    public SupplierOrder save(SupplierOrderData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
