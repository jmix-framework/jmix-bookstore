package io.jmix.bookstore.product;

import io.jmix.bookstore.test_support.Validations;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class ProductValidationTest {

    @Autowired
    private Validations validations;
    @Autowired
    private Products products;


    @Test
    void given_validProduct_when_validate_then_noViolationOccurs() {

        // given
        Product product = products.createDefault();

        // expect
        validations.assertNoViolations(product);
    }

    @Test
    void given_productWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        Product product = products.create(
                products.defaultData()
                        .name(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(product, "name", "NotNull");
    }
}
