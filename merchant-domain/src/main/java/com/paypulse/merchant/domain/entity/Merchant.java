package com.paypulse.merchant.domain.entity;

import java.time.Instant;
import java.util.UUID;

import com.paypulse.merchant.domain.enums.MerchantStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Merchant {

    private UUID merchantId;
    private String merchantName;
    private String merchantEmail;
    private String merchantPhone;
    private Instant createdAt;
    private Instant updatedAt;
    private MerchantStatus merchantStatus;
}
