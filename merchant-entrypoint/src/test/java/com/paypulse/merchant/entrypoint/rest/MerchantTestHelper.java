package com.paypulse.merchant.entrypoint.rest;

import java.util.UUID;

final class MerchantTestHelper {

    static final String REGISTER_MERCHANT_PATH = "/merchants/register";
    static final String RESOLVE_MERCHANT_PATH = "/merchants/resolve";

    private static final String DEFAULT_NAME = "Acme Store";
    private static final String DEFAULT_PHONE = "1234567890";

    private MerchantTestHelper() {
    }

    static String randomEmail(String prefix) {
        return prefix + "-" + UUID.randomUUID() + "@example.com";
    }

    static String validName() {
        return DEFAULT_NAME;
    }

    static String validPhone() {
        return DEFAULT_PHONE;
    }

    static String validEmail() {
        return randomEmail("merchant");
    }

    static String validRegisterPayload() {
        return registerPayload(validName(), validEmail(), validPhone());
    }

    static String invalidEmailPayload() {
        return registerPayload(validName(), "invalid-email", validPhone());
    }

    static String emptyNamePayload() {
        return registerPayload("", validEmail(), validPhone());
    }

    static String registerPayload(String name, String email, String phone) {
        return "{" +
                "\"name\":\"" + name + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"phone\":\"" + phone + "\"" +
                "}";
    }
}
