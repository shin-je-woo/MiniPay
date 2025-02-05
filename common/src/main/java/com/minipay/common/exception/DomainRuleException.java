package com.minipay.common.exception;

public class DomainRuleException extends RuntimeException {

    public DomainRuleException() {
        super();
    }

    public DomainRuleException(String message) {
        super(message);
    }
}
