package com.paypulse.merchant.adapters.out.outbox;

import java.time.Instant;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "outbox")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutboxJpaEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    /** Matches the envelope's eventId. */
    @Column(name = "event_id", nullable = false, updatable = false)
    private UUID eventId;

    /** Matches the envelope's eventType. */
    @Column(name = "event_type", nullable = false, updatable = false)
    private String eventType;

    /** Matches the envelope's correlationId; kept denormalized for ops queries and relay logging. */
    @Column(name = "correlation_id", nullable = false, updatable = false)
    private UUID correlationId;

    /** The full envelope JSON (payload included). */
    @Column(name = "payload", columnDefinition = "text", nullable = false, updatable = false)
    private String payload;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /** Null until the relay has published this row. */
    @Column(name = "published_at")
    private Instant publishedAt;

    public boolean isPublished() {
        return publishedAt != null;
    }
}
