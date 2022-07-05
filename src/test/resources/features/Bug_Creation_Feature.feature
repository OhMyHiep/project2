Feature: Report a Bug
  User can report a Bug

  Scenario Outline: Bug Creation
    Given I am logged in and on The bug submission page
    When I fill out the title
    When I fill out the description
    When I fill out the severity
    When I fill out urgency
    When I click submit
    Then the