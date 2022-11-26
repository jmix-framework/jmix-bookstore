package io.jmix.bookstore.fulfillment;

import io.jmix.bookstore.fulfillment.test_support.FulfillmentCenters;
import io.jmix.bookstore.test_support.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class FulfillmentCenterValidationTest {

    @Autowired
    private Validations validations;
    @Autowired
    private FulfillmentCenters fulfillmentCenters;

    @Test
    void given_validFulfillmentCenter_when_validate_then_noViolationOccurs() {

        // given
        FulfillmentCenter fulfillmentCenter = fulfillmentCenters.createDefault();

        // expect
        validations.assertNoViolations(fulfillmentCenter);
    }

    @Test
    void given_fulfillmentCenterWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        FulfillmentCenter fulfillmentCenter = fulfillmentCenters.create(
                fulfillmentCenters.defaultData()
                        .name(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(fulfillmentCenter, "name", "NotNull");
    }

    @Test
    void given_fulfillmentCenterWithoutRegion_when_validate_then_oneViolationOccurs() {

        // given
        FulfillmentCenter fulfillmentCenter = fulfillmentCenters.create(
                fulfillmentCenters.defaultData()
                        .region(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(fulfillmentCenter, "region", "NotNull");
    }
}
