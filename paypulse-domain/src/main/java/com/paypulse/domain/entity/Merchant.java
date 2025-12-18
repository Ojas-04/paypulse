package com.paypulse.domain.entity;

import com.paypulse.domain.enums.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    public String merchantId;
    public String merchantName;
    public String merchantEmail;
    public String merchantPhone;
    public String createdAt;
    public String updatedAt;
    public MerchantStatus merchantStatus;
}
