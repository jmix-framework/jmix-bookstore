package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.test_support.EntityRepository;
import io.jmix.bookstore.product.Product;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component("bookstore_ProductRepository")
public class ProductRepository implements EntityRepository<ProductData, Product> {

    @Autowired
    ProductMapper mapper;

    @Autowired
    DataManager dataManager;

    public Product save(ProductData dto) {
        return dataManager.save(mapper.toEntity(dto));
    }
}
