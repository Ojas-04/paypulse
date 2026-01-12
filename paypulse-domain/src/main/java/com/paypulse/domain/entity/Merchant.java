package com.paypulse.domain.entity;

import com.paypulse.domain.enums.MerchantStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    private String merchantId;
    private String merchantName;
    private String merchantEmail;
    private String merchantPhone;
    private String createdAt;
    private String updatedAt;
    private MerchantStatus merchantStatus;
}
