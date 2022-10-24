package io.jmix.bookstore.product.supplier;


import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.supplier.test_support.SupplierOrderLines;
import io.jmix.bookstore.product.supplier.test_support.SupplierOrders;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.product.test_support.Suppliers;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class SupplierOrderLineStorageTest {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private Products products;
    @Autowired
    private SupplierOrders supplierOrders;
    @Autowired
    private Suppliers suppliers;
    @Autowired
    private SupplierOrderLines supplierOrderLines;

    @Test
    void given_validSupplierOrderWithOrderLines_when_save_then_supplierOrderAndOrderLinesAreStored() {

        // given
        Supplier supplier = suppliers.saveDefault();

        Product p1 = products.save(
                products.defaultData()
                        .supplier(supplier)
                        .unitPrice(products.money(BigDecimal.ONE, Currency.USD))
                        .build()
        );

        Product p2 = products.save(
                products.defaultData()
                        .supplier(supplier)
                        .unitPrice(products.money(BigDecimal.ONE, Currency.USD))
                        .build()
        );

        // and
        SupplierOrder supplierOrder = supplierOrders.create(
                supplierOrders.defaultData()
                        .supplier(supplier)
                        .build()
        );

        // and
        SupplierOrderLine ol1 = supplierOrderLines.create(
                supplierOrderLines.defaultData()
                        .product(p1)
                        .supplierOrder(supplierOrder)
                        .quantity(10)
                        .build()
        );
        SupplierOrderLine ol2 = supplierOrderLines.create(
                supplierOrderLines.defaultData()
                        .product(p2)
                        .supplierOrder(supplierOrder)
                        .quantity(10)
                        .build()
        );

        // and
        supplierOrder.setOrderLines(List.of(ol1, ol2));

        // when
        dataManager.save(supplierOrder);

        // then
        assertThat(loadSupplierOrder(supplierOrder).getOrderLines())
                .containsExactlyInAnyOrder(ol1, ol2);


    }


    private SupplierOrder loadSupplierOrder(SupplierOrder supplierOrder) {
        return dataManager.load(Id.of(supplierOrder))
                .fetchPlan(supplierOrderFp -> {
                    supplierOrderFp.addFetchPlan(FetchPlan.BASE);
                    supplierOrderFp.add("orderLines", FetchPlan.BASE);
                })
                .one();
    }
}
