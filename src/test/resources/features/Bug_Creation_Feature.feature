Feature: Report a Bug
  User can report a Bug

  Scenario Outline: Bug Creation
    Given I am logged in and on The bug submission page
    When I fill out the title "<title>"
    And I fill out the description "<description>"
    And I input out the "<severity>"
    And I set the "<urgency>"
    And I click submit
    Then a "<message>" confirmation will appear

    Examples:
      | title                         | description                                                                         | severity | urgency | message              |
      | example title from selenium   | this is a very long description that is supposed to be over fifty characters long   | 1        | 1       | Success              |
      | empty                         | dmvnclsdljcndsjljds    cdcsdcsdcsdcdsfffgfhbgbgbgbkdnfkvnknvknkvnknvkncknvncvmn     | 3        | 4       | Fill Out All Fields  |
      | example title from selenium   | invalid description                                                                 | 1        | 1       | Failed to submit bug |

