package com.minipay.common.helper;

import jakarta.validation.*;

import java.util.Set;

public abstract class SelfValidating<T> {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = FACTORY.getValidator();

    protected SelfValidating() {
    }

    protected void validateSelf() {
        @SuppressWarnings("unchecked")
        T self = (T) this;
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(self);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
