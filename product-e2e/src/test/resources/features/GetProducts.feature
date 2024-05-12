Feature: Fetching products from db

  Scenario: Get products
    When I request the available products
    Then I receive the available products