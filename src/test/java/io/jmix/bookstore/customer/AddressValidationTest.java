package io.jmix.bookstore.customer;

import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class AddressValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;

    private Address address;

    @BeforeEach
    void setUp() {
        address = dataManager.create(Address.class);
    }

    @Test
    void given_validAddress_when_validateAddress_then_noViolationOccurs() {

        // given
        address.setStreet("Sesame Street");

        // expect
        validations.assertNoViolations(address);
    }
    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_oneViolationOccurs() {

        // given
        address.setStreet(null);

        // expect
        validations.assertOneViolationWith(address, "street", "NotBlank");
    }

}
