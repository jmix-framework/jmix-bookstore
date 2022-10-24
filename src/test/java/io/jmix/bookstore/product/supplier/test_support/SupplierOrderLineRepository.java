package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierOrderLineRepository implements EntityRepository<SupplierOrderLineData, SupplierOrderLine> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;

    @Autowired
    SupplierOrderLineMapper mapper;

    @Override
    public SupplierOrderLine save(SupplierOrderLineData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
