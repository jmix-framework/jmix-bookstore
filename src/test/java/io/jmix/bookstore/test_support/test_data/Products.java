package io.jmix.bookstore.test_support.test_data;

import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.test_support.ProductData;
import io.jmix.bookstore.product.test_support.ProductMapper;
import io.jmix.bookstore.product.test_support.ProductRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class Products
        implements TestDataProvisioning<ProductData, ProductData.ProductDataBuilder, Product> {

    @Autowired
    DataManager dataManager;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductRepository productRepository;

    public static String DEFAULT_NAME = "product_name";

    @Override
    public ProductData.ProductDataBuilder defaultData() {
        return ProductData.builder()
                .name(DEFAULT_NAME)
                .category(null);
    }

    @Override
    public Product save(ProductData productData)  {
        return productRepository.save(productData);
    }

    @Override
    public Product create(ProductData productData) {
        return productMapper.toEntity(productData);
    }

    @Override
    public Product createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Product saveDefault() {
        return save(defaultData().build());
    }

}
