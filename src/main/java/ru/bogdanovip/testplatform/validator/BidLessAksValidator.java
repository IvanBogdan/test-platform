package ru.bogdanovip.testplatform.validator;

import ru.bogdanovip.testplatform.model.Quote;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BidLessAksValidator implements ConstraintValidator<BidLessAks, Quote> {

    @Override
    public boolean isValid(Quote quote, ConstraintValidatorContext constraintValidatorContext) {
        return (quote.getBid() == null)
                || (quote.getAsk() == null)
                || (quote.getBid() < quote.getAsk());
    }
}
