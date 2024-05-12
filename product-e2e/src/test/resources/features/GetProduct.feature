Feature: Get a product

  Scenario: Fetching a product
    Given a product exists in the system
    When I request the details of the product
    Then I receive the product