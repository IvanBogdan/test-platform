package ru.bogdanovip.testplatform.validator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BidLessAksValidator.class)
public @interface BidLessAks {

    String message() default "bid should be less then ask";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
