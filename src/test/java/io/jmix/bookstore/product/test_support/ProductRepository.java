package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class ProductRepository implements EntityRepository<ProductData, Product> {

    @Autowired
    ProductMapper mapper;
    @Autowired
    EntityValidation entityValidation;
    @Autowired
    DataManager dataManager;

    public Product save(ProductData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }
}
