package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.product.supplier.test_support.EntityRepository;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierRepository implements EntityRepository<SupplierData, Supplier> {

    @Autowired
    SupplierMapper mapper;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    DataManager dataManager;

    public Supplier save(SupplierData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }
}
