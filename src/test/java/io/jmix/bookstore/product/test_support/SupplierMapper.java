package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import io.jmix.bookstore.product.supplier.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface SupplierMapper {

    Supplier toEntity(SupplierData productData);
}
