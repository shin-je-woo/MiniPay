package com.minipay.banking.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "outbox")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OutboxJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("이벤트 식별자")
    @Column(name = "event_id", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    private UUID eventId;

    @Comment("직렬화된 이벤트 본문")
    @Lob
    @Column(name = "serialized_event", nullable = false)
    private String serializedEvent;

    @Comment("이벤트 발행 여부")
    @Column(name = "published", nullable = false)
    private boolean published;

    @Comment("이벤트 생성 일시")
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Comment("이벤트 발행 일시")
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    public void markAsPublished() {
        this.published = true;
        this.publishedAt = LocalDateTime.now();
    }
}
