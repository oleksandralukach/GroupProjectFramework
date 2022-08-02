Feature: Basic Validation
@Smoke
  Scenario Outline: Google title verification
    When the user navigates to google
    And the user searches for "<searchItem>"
  ##And I get user with id "1" from database
    Then verify "<resultItem>" is in the title of the page
  Examples:
    | searchItem | resultItem|
    | apple | apple          |
    | cat | dog              |

  @Smoke
  Scenario: Google title verification 2
    When the user navigates to google
    And the user searches for "dog"
    Then verify "dog" is in the title of the page
