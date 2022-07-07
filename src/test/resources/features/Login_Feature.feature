Feature: As a user, As a user, I want to login

  Scenario Outline: User want to login as manager with valid input details
    Given I am at loginPage
    When I enter username as "<username>"
    When I enter password as "<password>"
    When I click Login button
    Then I can see all bug page

    Examples:
      | username        | password |
      | email@email.com | pass     |

  Scenario Outline: User want to login as tester with valid input details
    Given I am at loginPage
    When I enter username as "<username>"
    When I enter password as "<password>"
    When I click Login button
    Then I can see all bug page

    Examples:
      | username        | password |
      | test@email.com  | test     |


  Scenario Outline: User want to login with invalid input details
    Given I am at loginPage
    When I enter username as "<username>"
    When I enter password as "<password>"
    When I click Login button
    Then I can the error message as "<errorMessage>"

    Examples:
      | username       |password | errorMessage          |
      |username        |test     | Invalid login!        |
      |empty           |password | Must not be empty!    |
      |test@email.com  |empty    | Must not be empty!    |