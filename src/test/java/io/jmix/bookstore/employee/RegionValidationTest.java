package io.jmix.bookstore.employee;

import io.jmix.bookstore.employee.test_support.Regions;
import io.jmix.bookstore.test_support.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class RegionValidationTest {

    @Autowired
    private Validations validations;
    @Autowired
    private Regions regions;


    @Test
    void given_validRegion_when_validate_then_noViolationOccurs() {

        // given
        Region region = regions.createDefault();

        // expect
        validations.assertNoViolations(region);
    }

    @Test
    void given_regionWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        Region region = regions.create(
                regions.defaultData()
                        .name(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(region, "name", "NotNull");
    }
}
