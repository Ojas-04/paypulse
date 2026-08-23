package com.paypulse.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterMerchantRequest {

    @NotBlank
    public String name;

    @Email
    public String email;

    public String phone;
}
