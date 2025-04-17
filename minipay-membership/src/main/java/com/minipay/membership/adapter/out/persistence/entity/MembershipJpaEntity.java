package com.minipay.membership.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "membership")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembershipJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "membership_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;
}
