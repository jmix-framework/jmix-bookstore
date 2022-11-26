package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.product.test_support.SupplierOrderRequests;
import io.jmix.bookstore.test_support.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class SupplierOrderRequestValidationTest {

    @Autowired
    private Validations validations;
    @Autowired
    private SupplierOrderRequests supplierOrderRequests;


    @Test
    void given_validSupplierOrderRequests_when_validate_then_noViolationOccurs() {

        // given
        var entity = supplierOrderRequests.createDefault();

        // expect
        validations.assertNoViolations(entity);
    }

    @Test
    void given_supplierOrderRequestWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given
        var entity = supplierOrderRequests.create(
                supplierOrderRequests.defaultData()
                        .product(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(entity, "product", "NotNull");
    }
}
