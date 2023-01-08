package io.jmix.bookstore.order.entity;

import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.test_support.Validations;
import io.jmix.bookstore.order.test_support.OrderLines;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration-test")
class OrderLineValidationTest {

    @Autowired
    Validations validations;
    @Autowired
    OrderLines orderLines;


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
}
