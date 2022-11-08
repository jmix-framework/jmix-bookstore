package io.jmix.bookstore.product.supplier;


import io.jmix.bookstore.product.test_support.Suppliers;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.jmix.bookstore.order.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class SupplierStorageTest {

    @Autowired
    Suppliers suppliers;

    @Test
    void given_validSupplier_when_saveSupplier_then_supplierIsSaved() {

        // given
        Supplier savedSupplier = suppliers.save(
                suppliers.defaultData()
                        .name("Foo Supplier")
                        .address(suppliers.defaultAddress())
                        .build()
        );

        // then
        assertThat(savedSupplier)
                .hasName("Foo Supplier");
    }
}
