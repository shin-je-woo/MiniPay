package com.minipay.membership.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@ToString
@Table(name = "membership")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembershipJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;
}
