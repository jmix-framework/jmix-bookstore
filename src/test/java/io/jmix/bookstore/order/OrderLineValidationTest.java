package io.jmix.bookstore.order;

import io.jmix.bookstore.test_support.Validations;
import io.jmix.bookstore.test_support.test_data.OrderLines;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class OrderLineValidationTest {

    @Autowired
    Validations validations;
    @Autowired
    OrderLines orderLines;

    private final LocalDateTime NOW = LocalDateTime.now();
    private final LocalDateTime YESTERDAY = NOW.minusDays(1);
    private final LocalDateTime TOMORROW = NOW.plusDays(1);
    private final LocalDateTime IN_TWO_DAYS = NOW.plusDays(2);


    @Test
    void given_validOrderLine_when_validate_then_noViolationOccurs() {

        // given
        OrderLine orderLine = orderLines.createDefault();

        // expect
        validations.assertNoViolations(orderLine);
    }

    @Test
    void given_orderLineWithoutOrder_when_validate_then_oneViolationOccurs() {

        // given
        OrderLine orderLine = orderLines.create(
                orderLines.defaultData()
                        .order(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(orderLine, "order", "NotNull");
    }

    @Test
    void given_orderLineWithStartsAtNotPresent_when_validate_then_oneViolationOccurs() {

        // given
        OrderLine orderLine = orderLines.create(
                orderLines.defaultData()
                        .startsAt(null)
                        .build()
        );

        // expect
        validations.assertOneViolationWith(orderLine, "startsAt", "NotNull");
    }

    @Test
    void given_orderLineWithEndsAtNotPresent_when_validate_then_oneViolationOccurs() {

        // given
        OrderLine orderLine = orderLines.create(
                orderLines.defaultData()
                        .endsAt(null)
                        .build()
        );

        // expect
        validations.assertOneViolationWith(orderLine, "endsAt", "NotNull");
    }

    @Test
    void given_orderLineWithStartsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given
        OrderLine orderLine = orderLines.create(
                orderLines.defaultData()
                        .startsAt(YESTERDAY)
                        .endsAt(IN_TWO_DAYS)
                        .build()
        );

        // expect
        validations.assertOneViolationWith(orderLine, "startsAt", "FutureOrPresent");
    }

    @Test
    void given_orderLineWithEndsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given
        OrderLine orderLine = orderLines.create(
                orderLines.defaultData()
                        .startsAt(TOMORROW)
                        .endsAt(YESTERDAY)
                        .build()
        );

        // expect
        validations.assertOneViolationWith(orderLine, "endsAt", "FutureOrPresent");
    }

    @Test
    void given_orderLineWithEndsBeforeTheStartDate_when_validate_then_oneViolationOccurs() {

        // given
        OrderLine orderLine = orderLines.create(
                orderLines.defaultData()
                        .startsAt(IN_TWO_DAYS)
                        .endsAt(YESTERDAY)
                        .build()
        );

        // expect
        validations.assertOneViolationWith(orderLine, "ValidRentalPeriod");
    }
}
