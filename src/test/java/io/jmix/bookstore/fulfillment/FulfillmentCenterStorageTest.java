package io.jmix.bookstore.fulfillment;


import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.test_support.Regions;
import io.jmix.bookstore.fulfillment.test_support.FulfillmentCenters;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.jmix.bookstore.entity.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class FulfillmentCenterStorageTest {

    @Autowired
    FulfillmentCenters fulfillmentCenters;
    @Autowired
    Regions regions;

    @Test
    void given_validFulfillmentCenter_when_saveFulfillmentCenter_then_fulfillmentCenterIsSaved() {

        // given
        Region region = regions.saveDefault();

        // given
        FulfillmentCenter savedFulfillmentCenter = fulfillmentCenters.save(
                fulfillmentCenters.defaultData()
                        .name("Fulfillment Center")
                        .region(region)
                        .build()
        );

        // then
        assertThat(savedFulfillmentCenter)
                .hasName("Fulfillment Center")
                .hasRegion(region);
    }
}
