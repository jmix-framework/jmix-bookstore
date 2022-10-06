package io.jmix.bookstore.order.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRentalPeriodValidator.class)
public @interface ValidRentalPeriod {

    String message() default "{javax.validation.constraints.ValidRentalPeriod.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
