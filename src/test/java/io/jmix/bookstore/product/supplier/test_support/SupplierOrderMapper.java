package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface SupplierOrderMapper {

    SupplierOrder toEntity(SupplierOrderData supplierOrderData);
}
