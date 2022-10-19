package io.jmix.bookstore.product;

import io.jmix.bookstore.test_support.Validations;
import io.jmix.bookstore.product.test_support.ProductCategories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class ProductCategoryValidationTest {

    @Autowired
    private Validations validations;

    @Autowired
    private ProductCategories productCategories;


    @Test
    void given_validProductCategory_when_validate_then_noViolationOccurs() {

        // given
        ProductCategory productCategory = productCategories.createDefault();

        // expect
        validations.assertNoViolations(productCategory);
    }

    @NullSource
    @EmptySource
    @ParameterizedTest
    void given_productCategoryWithoutUnit_when_validate_then_oneViolationOccurs(String name) {

        // given
        ProductCategory productCategory = productCategories.create(
                productCategories.defaultData()
                        .name(name)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(productCategory, "name", "NotBlank");
    }
}
