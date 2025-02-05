package com.minipay.common.exception;

public class DomainNotFoundException extends RuntimeException {

    public DomainNotFoundException() {
        super();
    }

    public DomainNotFoundException(String message) {
        super(message);
    }
}
