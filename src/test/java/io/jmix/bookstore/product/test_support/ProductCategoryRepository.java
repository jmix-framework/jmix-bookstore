package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.test_support.EntityValidation;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class ProductCategoryRepository implements EntityRepository<ProductCategoryData, ProductCategory> {

    @Autowired
    DataManager dataManager;
    @Autowired
    EntityValidation entityValidation;

    @Autowired
    ProductCategoryMapper mapper;

    @Override
    public ProductCategory save(ProductCategoryData dto) {
        var entity = mapper.toEntity(dto);
        entityValidation.ensureIsValid(entity);
        return dataManager.save(entity);
    }

}
