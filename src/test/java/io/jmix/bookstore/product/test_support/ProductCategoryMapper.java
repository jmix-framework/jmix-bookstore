package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import io.jmix.bookstore.product.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface ProductCategoryMapper {

    ProductCategory toEntity(ProductCategoryData productCategoryData);
}
