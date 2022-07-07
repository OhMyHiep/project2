Feature: As a user, I want to view bug reports

  Scenario: Tests what happens when try to access page without logging in
    Given I am at loginPage
    When I tried to enter page url
    Then I will get back to login page

  Scenario: Tests routes to the right page
    Given I am at loginPage
    When I login with valid inputs
    When I click on view full details of first bug
    Then I will go to bug page
