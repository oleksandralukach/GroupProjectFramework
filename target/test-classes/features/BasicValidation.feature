Feature: Basic Validation
@Smoke
  Scenario: Google title verification
    When the user navigates to google
    And the user searches for "apple"
  ##And I get user with id "1" from database
    Then verify "dog" is in the title of the page