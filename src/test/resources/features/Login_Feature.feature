Feature: As a user, As a user, I want to login

  Scenario Outline: User want to login as manager with valid input details
    Given I am at loginPage
    When I enter username as "<username>"
    When I enter password as "<password>"
    Then I can see all the bugs

    Examples:
      | username        | password |
      | email@email.com | pass     |

  Scenario Outline: User want to login as tester with valid input details
    Given I am at loginPage
    When I enter username as "<username>"
    When I enter password as "<password>"
    Then I can see all the bugs

    Examples:
      | username        | password |
      | test@email.com  | test     |