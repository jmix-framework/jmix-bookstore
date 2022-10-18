package io.jmix.bookstore.product.supplier;


import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.product.test_support.SupplierOrderRequests;
import io.jmix.bookstore.product.test_support.Suppliers;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration-test")
class PerformSupplierOrderServiceTest {

    @Autowired
    PerformSupplierOrderService performSupplierOrderService;


    @Autowired
    PerformSupplierOrderService performSupplierOrderService;

    @Autowired
    SupplierOrderRequests supplierOrderRequests;
    @Autowired
    Products products;

    @Autowired
    Suppliers suppliers;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    DataManager dataManager;


    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();
    }

    @Test
    void createDraftSupplierOrders() {

        // given:
        Supplier supplier1 = suppliers.saveDefault();
        Supplier supplier2 = suppliers.saveDefault();

        // and:
        Product product11 = products.save(
                products.defaultData()
                        .supplier(supplier1)
                        .build()
        );
        Product product12 = products.save(
                products.defaultData()
                        .supplier(supplier1)
                        .build()
        );
        // and:
        Product product21 = products.save(
                products.defaultData()
                        .supplier(supplier2)
                        .build()
        );
        Product product22 = products.save(
                products.defaultData()
                        .supplier(supplier2)
                        .build()
        );

        // and:
        supplierOrderRequests.save(
                supplierOrderRequests.defaultData()
                        .product(product11)
                        .build()
        );
        supplierOrderRequests.save(
                supplierOrderRequests.defaultData()
                        .product(product12)
                        .build()
        );

        // when:
        performSupplierOrderService.createDraftSupplierOrders();

        // then:
        List<SupplierOrder> supplierOrders = dataManager.load(SupplierOrder.class)
                .all()
                .list();

        assertThat(supplierOrders)
                .hasSize(1);

    }
}
