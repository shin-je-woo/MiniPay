package com.minipay.membership.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class LoginMembershipCommand extends SelfValidating<LoginMembershipCommand> {

    @NotNull
    @NotBlank
    private final String email;

    @NotNull
    @NotBlank
    private final String password;

    @Builder
    public LoginMembershipCommand(String email, String password) {
        this.email = email;
        this.password = password;

        this.validateSelf();
    }
}
