package com.minipay.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topic {
    LOGGING("logging.out.stdout");

    private final String name;
}
