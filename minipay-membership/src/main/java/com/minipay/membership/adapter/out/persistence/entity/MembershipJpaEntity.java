package com.minipay.membership.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
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

    @Comment("회원 식별자")
    @Column(name = "membership_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID membershipId;

    @Comment("회원 이름")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Comment("회원 이메일")
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Comment("회원 주소")
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Comment("회원 유효 여부")
    @Column(name = "is_valid", nullable = false)
    private boolean isValid;

    @Comment("법인 여부")
    @Column(name = "is_corp", nullable = false)
    private boolean isCorp;
}
