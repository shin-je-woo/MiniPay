package com.minipay.membership.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReIssueTokenCommand extends SelfValidating<ReIssueTokenCommand> {

    @NotNull
    @NotBlank
    private final String refreshToken;

    @Builder
    public ReIssueTokenCommand(String refreshToken) {
        this.refreshToken = refreshToken;

        this.validateSelf();
    }
}
