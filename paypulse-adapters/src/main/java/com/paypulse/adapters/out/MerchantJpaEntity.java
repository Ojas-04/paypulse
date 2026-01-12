package com.paypulse.adapters.out;

import com.paypulse.domain.enums.MerchantStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "merchants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantJpaEntity extends PanacheEntityBase {

    @Id
    @Column(name = "merchant_id", nullable = false, updatable = false)
    private String merchantId;

    @Column(name = "merchant_name", nullable = false)
    private String merchantName;

    @Column(name = "merchant_email", nullable = false, unique = true)
    private String merchantEmail;

    @Column(name = "merchant_phone")
    private String merchantPhone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MerchantStatus merchantStatus;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
}