package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.product.test_support.Suppliers;
import io.jmix.bookstore.test_support.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class SupplierValidationTest {

    @Autowired
    Validations validations;
    @Autowired
    private Suppliers suppliers;


    @Test
    void given_validSupplier_expect_noViolationOccurs() {

        // given
        Supplier supplier = suppliers.create(
                suppliers.defaultData()
                        .build()
        );

        // expect
        validations.assertNoViolations(supplier);
    }

    @Test
    void given_supplierWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        Supplier supplier = suppliers.create(
                suppliers.defaultData()
                        .name(null)
                        .build()
        );


        // expect
        validations.assertExactlyOneViolationWith(supplier, "name", "NotNull");
    }
    
    @Test
    void given_supplierWithInvalidEmail_when_validateSupplier_then_oneViolationOccurs() {

        // given
        Supplier supplier = suppliers.create(
                suppliers.defaultData()
                        .email("invalidEmailAddress")
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(supplier, "email", "Email");
    }
}
