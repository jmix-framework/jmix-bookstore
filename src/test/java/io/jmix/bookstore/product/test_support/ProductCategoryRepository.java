package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component("bookstore_ProductCategoryRepository")
public class ProductCategoryRepository implements EntityRepository<ProductCategoryData, ProductCategory> {

    @Autowired
    DataManager dataManager;

    @Autowired
    ProductCategoryMapper mapper;

    @Override
    public ProductCategory save(ProductCategoryData dto) {
        return dataManager.save(mapper.toEntity(dto));
    }

}
