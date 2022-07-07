Feature: Updating Bug
  update an existing bug

  Scenario Outline:
    Given I am logged in and I am on The detail Bug page
    When I chose a severity "<severity>"
    And I input an urgency "<urgency>"
    And I set the Assignee "<Assignee>"
    And I chose a status "<status>"
    And I click update
    Then I will see the feedback "<feedback>"

    Examples:
      | severity | urgency |status |Assignee | feedback               |
      | 1        | 2       | 2     |         | Update Success         |
      | 4        |         |       |         | Update Success         |
      |          | 2       |       |         | Update Success         |
      |          |         | 2     |         | Update Success         |
      |          |         |       |   1     | Update Success         |
