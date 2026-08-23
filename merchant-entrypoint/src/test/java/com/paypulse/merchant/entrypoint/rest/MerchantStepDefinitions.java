package com.paypulse.merchant.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class MerchantStepDefinitions {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static final class MerchantResponse {
        public String merchantId;
        public String merchantName;
        public String merchantEmail;
        public String merchantPhone;
        public String merchantStatus;
    }

    private String payload;
    private Response response;
    private String email;

    @Given("a valid merchant registration request")
    public void a_valid_merchant_registration_request() {
        email = MerchantTestHelper.validEmail();
        payload = MerchantTestHelper.registerPayload(
                MerchantTestHelper.validName(),
                email,
                MerchantTestHelper.validPhone()
        );
    }

    @Given("a merchant registration request with invalid email")
    public void a_merchant_registration_request_with_invalid_email() {
        payload = MerchantTestHelper.invalidEmailPayload();
    }

    @Given("a duplicate merchant registration request")
    public void a_duplicate_merchant_registration_request() {
        email = MerchantTestHelper.randomEmail("duplicate");
        payload = MerchantTestHelper.registerPayload("Duplicate Store", email, "9876543210");
    }

    @When("the client registers the merchant")
    public void the_client_registers_the_merchant() {
        response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(MerchantTestHelper.REGISTER_MERCHANT_PATH)
                .then()
                .extract()
                .response();
    }

    @When("the client registers the merchant again")
    public void the_client_registers_the_merchant_again() {
        the_client_registers_the_merchant();
    }

    @Then("the response status code is {int}")
    public void the_response_status_code_is(Integer statusCode) {
        assertThat(response.statusCode(), is(statusCode));
    }

    @And("the response contains the registered merchant data")
    public void the_response_contains_the_registered_merchant_data() {
        MerchantResponse responseBody = response.as(MerchantResponse.class);
        assertThat(responseBody.merchantId, notNullValue());
        assertThat(responseBody.merchantName, equalTo("Acme Store"));
        assertThat(responseBody.merchantEmail, equalTo(email));
        assertThat(responseBody.merchantPhone, equalTo("1234567890"));
        assertThat(responseBody.merchantStatus, equalTo("ACTIVE"));
    }

    @And("the error message is present")
    public void the_error_message_is_present() {
        assertThat(response.jsonPath().getString("message"), notNullValue());
    }

    @And("the error code is {string}")
    public void the_error_code_is(String code) {
        assertThat(response.jsonPath().getString("errorCode"), equalTo(code));
    }
}

