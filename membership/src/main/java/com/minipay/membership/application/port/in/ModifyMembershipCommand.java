package com.minipay.membership.application.port.in;

import com.minipay.common.helper.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {

    @NotNull
    private final UUID membershipId;

    @NotNull
    @NotBlank
    private final String name;

    @NotNull
    @NotBlank
    private final String email;

    @NotNull
    @NotBlank
    private final String address;

    @Builder
    public ModifyMembershipCommand(UUID membershipId, String name, String email, String address) {
        this.membershipId = membershipId;
        this.name = name;
        this.email = email;
        this.address = address;

        this.validateSelf();
    }
}
