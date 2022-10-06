package io.jmix.bookstore.customer;

import io.jmix.bookstore.test_support.Validations;
import io.jmix.bookstore.test_support.test_data.Customers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerValidationTest {

    @Autowired
    Validations validations;
    @Autowired
    private Customers customers;


    @Test
    void given_validCustomer_expect_noViolationOccurs() {

        // given
        Customer customer = customerWithEmail("valid@email.address");

        // expect
        validations.assertNoViolations(customer);
    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_oneViolationOccurs() {

        // given
        Customer customer = customerWithEmail("invalidEmailAddress");

        // expect
        validations.assertExactlyOneViolationWith(customer, "email", "Email");
    }


    private Customer customerWithEmail(String email) {
        return customers.create(
                customers.defaultData()
                        .email(email)
                        .build()
        );
    }
}
