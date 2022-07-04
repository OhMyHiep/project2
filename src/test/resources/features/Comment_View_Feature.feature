Feature: Comment View
  As a user, I want to view comments on a bug

  Scenario: Bug with comments
    Given I am logged in as John Smith
    When I click on a bug with comments
    Then I can see comments

  Scenario: Bug without comments
    Given I am logged in as John Smith
    When I click on a bug with no comments
    Then I should see no comments