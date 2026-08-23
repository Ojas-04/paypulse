Feature: Register merchant

  Scenario: Register merchant successfully
    Given a valid merchant registration request
    When the client registers the merchant
    Then the response status code is 200
    And the response contains the registered merchant data

  Scenario: Register merchant with invalid email
    Given a merchant registration request with invalid email
    When the client registers the merchant
    Then the response status code is 400
    And the error message is present

  Scenario: Register merchant with duplicate email
    Given a duplicate merchant registration request
    When the client registers the merchant
    And the client registers the merchant again
    Then the response status code is 409
    And the error code is "409"
    And the error message is present

