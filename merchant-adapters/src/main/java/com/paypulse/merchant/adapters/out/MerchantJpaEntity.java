package com.paypulse.merchant.adapters.out;

import java.time.Instant;
import java.util.UUID;

import com.paypulse.merchant.domain.enums.MerchantStatus;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "merchants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantJpaEntity extends PanacheEntityBase {

    @Id
    @Column(name = "merchant_id", nullable = false, updatable = false)
    private UUID merchantId;

    /** Unique — the idempotency anchor for get-or-create (§6, §7.3). */
    @Column(name = "merchant_name", nullable = false, unique = true)
    private String merchantName;

    @Column(name = "merchant_email", unique = true)
    private String merchantEmail;

    @Column(name = "merchant_phone")
    private String merchantPhone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MerchantStatus merchantStatus;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
