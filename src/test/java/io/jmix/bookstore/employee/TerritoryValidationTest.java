package io.jmix.bookstore.employee;

import io.jmix.bookstore.employee.test_support.Territories;
import io.jmix.bookstore.test_support.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class TerritoryValidationTest {

    @Autowired
    private Validations validations;
    @Autowired
    private Territories territories;

    @Test
    void given_validTerritory_when_validate_then_noViolationOccurs() {

        // given
        Territory territory = territories.createDefault();

        // expect
        validations.assertNoViolations(territory);
    }

    @Test
    void given_territoryWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        Territory territory = territories.create(
                territories.defaultData()
                        .name(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(territory, "name", "NotNull");
    }

    @Test
    void given_territoryWithoutRegion_when_validate_then_oneViolationOccurs() {

        // given
        Territory territory = territories.create(
                territories.defaultData()
                        .region(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(territory, "region", "NotNull");
    }
}
