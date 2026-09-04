Feature: Merchant registration and resolution
  Idempotent get-or-create registration + resolve by name/id (§6, §7, §7.3)

  Scenario: Register merchant successfully
    Given a valid merchant registration request
    When the client registers the merchant
    Then the response status code is 200
    And the response contains the registered merchant data

  Scenario: Register duplicate merchant returns the existing one
    Given a duplicate merchant registration request
    When the client registers the merchant
    And the client registers the merchant again
    Then the response status code is 200
    And both responses carry the same merchantId

  Scenario: Resolve merchant by name
    Given a registered merchant
    When the client resolves the merchant by name
    Then the response status code is 200
    And the response contains the resolved merchant data

  Scenario: Resolve merchant by id
    Given a registered merchant
    When the client resolves the merchant by id
    Then the response status code is 200
    And the response contains the resolved merchant data

  Scenario: Resolve merchant by name and matching id
    Given a registered merchant
    When the client resolves the merchant by name and matching id
    Then the response status code is 200
    And the response contains the resolved merchant data

  Scenario: Resolve a merchant that does not exist
    Given a registered merchant
    When the client resolves a merchant that does not exist
    Then the response status code is 404
    And the error code is "MERCHANT_NOT_FOUND"

  Scenario: Resolve without any identifier
    Given a registered merchant
    When the client resolves a merchant without any identifier
    Then the response status code is 400
    And the error code is "VALIDATION_FAILED"

  Scenario: Resolve with an invalid merchantId
    Given a registered merchant
    When the client resolves a merchant with an invalid id
    Then the response status code is 400
    And the error code is "VALIDATION_FAILED"
