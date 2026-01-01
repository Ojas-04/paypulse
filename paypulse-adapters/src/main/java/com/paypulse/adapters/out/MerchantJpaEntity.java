package com.paypulse.adapters.out;

import com.paypulse.domain.enums.MerchantStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "merchants")
public class MerchantJpaEntity extends PanacheEntityBase {

    @Id
    @Column(name = "merchant_id", nullable = false, updatable = false)
    public String merchantId;

    @Column(name = "merchant_name", nullable = false)
    public String merchantName;

    @Column(name = "merchant_email", nullable = false, unique = true)
    public String merchantEmail;

    @Column(name = "merchant_phone")
    public String merchantPhone;

    @Column(name = "status")
    public MerchantStatus merchantStatus;

    @Column(name = "created_at")
    public String createdAt;

    @Column(name = "updated_at")
    public String updatedAt;
}
