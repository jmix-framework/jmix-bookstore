package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.entity.test_support.Users;
import io.jmix.bookstore.product.test_support.SupplierOrderRequests;
import io.jmix.bookstore.test_support.Validations;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private Users users;
    private User user;


    @BeforeEach
    void setUp() {
        user = users.createDefault();
    }

    @Test
    void given_validSupplierOrderRequests_when_validate_then_noViolationOccurs() {

        // given
        var entity = supplierOrderRequests.defaultData()
                .requestedBy(user);

        // expect
        validations.assertNoViolations(entity);
    }

    @Test
    void given_supplierOrderRequestWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given
        var entity = supplierOrderRequests.create(
                supplierOrderRequests.defaultData()
                        .requestedBy(user)
                        .product(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(entity, "product", "NotNull");
    }
    @Test
    void given_supplierOrderRequestWithoutRequestor_when_validate_then_oneViolationOccurs() {

        // given
        var entity = supplierOrderRequests.create(
                supplierOrderRequests.defaultData()
                        .requestedBy(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(entity, "requestedBy", "NotNull");
    }
}
