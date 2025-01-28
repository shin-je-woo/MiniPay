package com.minipay.banking.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "bank_account")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccountJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long ownerId;

    private String bankName;

    private String accountNumber;

    private Boolean linkedStatusIsValid;
}
