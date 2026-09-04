package com.paypulse.merchant.entrypoint.rest;

import java.util.UUID;

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
    private Response firstDuplicateResponse;
    private String email;
    private String registeredMerchantId;

    @Given("a registered merchant")
    public void a_registered_merchant() {
        email = MerchantTestHelper.validEmail();
        payload = MerchantTestHelper.registerPayload(
                MerchantTestHelper.validName(),
                email,
                MerchantTestHelper.validPhone()
        );
        Response register = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(MerchantTestHelper.REGISTER_MERCHANT_PATH)
                .then()
                .extract()
                .response();
        registeredMerchantId = register.jsonPath().getString("merchantId");
        assertThat(registeredMerchantId, notNullValue());
    }

    @When("the client resolves the merchant by name")
    public void the_client_resolves_the_merchant_by_name() {
        // Case-insensitive: registered as "Acme Store", queried as lowercase.
        response = given()
                .queryParam("name", MerchantTestHelper.validName().toLowerCase())
                .when()
                .get(MerchantTestHelper.RESOLVE_MERCHANT_PATH)
                .then()
                .extract()
                .response();
    }

    @When("the client resolves the merchant by id")
    public void the_client_resolves_the_merchant_by_id() {
        response = given()
                .queryParam("id", registeredMerchantId)
                .when()
                .get(MerchantTestHelper.RESOLVE_MERCHANT_PATH)
                .then()
                .extract()
                .response();
    }

    @When("the client resolves the merchant by name and matching id")
    public void the_client_resolves_by_name_and_matching_id() {
        response = given()
                .queryParam("name", MerchantTestHelper.validName())
                .queryParam("id", registeredMerchantId)
                .when()
                .get(MerchantTestHelper.RESOLVE_MERCHANT_PATH)
                .then()
                .extract()
                .response();
    }

    @When("the client resolves a merchant that does not exist")
    public void the_client_resolves_a_non_existent_merchant() {
        response = given()
                .queryParam("name", "No Such Merchant " + UUID.randomUUID())
                .when()
                .get(MerchantTestHelper.RESOLVE_MERCHANT_PATH)
                .then()
                .extract()
                .response();
    }

    @When("the client resolves a merchant without any identifier")
    public void the_client_resolves_without_identifier() {
        response = given()
                .when()
                .get(MerchantTestHelper.RESOLVE_MERCHANT_PATH)
                .then()
                .extract()
                .response();
    }

    @When("the client resolves a merchant with an invalid id")
    public void the_client_resolves_with_invalid_id() {
        response = given()
                .queryParam("id", "not-a-uuid")
                .when()
                .get(MerchantTestHelper.RESOLVE_MERCHANT_PATH)
                .then()
                .extract()
                .response();
    }

    @And("the response contains the resolved merchant data")
    public void the_response_contains_the_resolved_merchant_data() {
        String bodyId = response.jsonPath().getString("merchantId");
        assertThat(bodyId, notNullValue());
        assertThat(bodyId, equalTo(registeredMerchantId));
        assertThat(response.jsonPath().getString("merchantName"), equalTo(MerchantTestHelper.validName()));
    }
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
        // Unique per run so scenarios stay isolated; the same name is posted twice below
        String name = "Duplicate Store " + UUID.randomUUID();
        payload = MerchantTestHelper.registerPayload(name, email, MerchantTestHelper.validPhone());
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
        firstDuplicateResponse = response;
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
        assertThat(responseBody.merchantName, equalTo(MerchantTestHelper.validName()));
        assertThat(responseBody.merchantEmail, equalTo(email));
        assertThat(responseBody.merchantPhone, equalTo(MerchantTestHelper.validPhone()));
        assertThat(responseBody.merchantStatus, equalTo("ACTIVE"));
    }

    @And("both responses carry the same merchantId")
    public void both_responses_carry_the_same_merchant_id() {
        String firstId = firstDuplicateResponse.jsonPath().getString("merchantId");
        String secondId = response.jsonPath().getString("merchantId");
        assertThat(firstId, notNullValue());
        assertThat(secondId, equalTo(firstId));
    }

    @And("the error message is present")
    public void the_error_message_is_present() {
        assertThat(response.jsonPath().getString("message"), notNullValue());
    }

    @And("the error timestamp and traceId are present")
    public void the_error_timestamp_and_trace_id_are_present() {
        assertThat(response.jsonPath().getString("timestamp"), notNullValue());
        assertThat(response.jsonPath().getString("traceId"), notNullValue());
    }

    @And("the error code is {string}")
    public void the_error_code_is(String code) {
        assertThat(response.jsonPath().getString("errorCode"), equalTo(code));
    }
}
