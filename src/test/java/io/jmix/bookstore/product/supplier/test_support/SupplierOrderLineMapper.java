package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.entity.test_support.JmixEntityFactory;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface SupplierOrderLineMapper {

    SupplierOrderLine toEntity(SupplierOrderLineData supplierOrderLineData);
}
