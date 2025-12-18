package com.paypulse.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterMerchantCommand {

    public String merchantName;
    public String merchantEmail;
    public String merchantPhone;

}
