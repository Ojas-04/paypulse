package com.paypulse.merchant.entrypoint.rest;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.paypulse.merchant.entrypoint.rest")
public class MerchantRegistrationTest extends CucumberQuarkusTest {
}
