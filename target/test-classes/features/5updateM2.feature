@UpdateBookMember2
Feature: Book Update Scenarios by Member2
  Scenario Outline: Book Update by Different validation Types Member2
    Given I am logged in as "<username>" with password "<password>" to update as "<userType>" Member2
    When I send a PUT request to "/api/books/<bookId>" with the following data Member2:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode> for put Member2

    Examples:
      | username   | password | userType     | bookId | bookTitle            | bookAuthor          | expectedStatusCode |
      | user       | password | user         | 1      | The Alchemist    s2  |                     | 403                |
      | guest      | password | guest         | 1      | The Alchemist    s2 |                     | 401                |
      | guest      | password | guest         | 1      |                     |        author abc   | 401                |

  Scenario Outline: Update Book with Validation Errors Member2
    Given I am logged in as "admin" with password "password" to update as admin Member2
    When I send a PUT request to "/api/books/3" with the following data Member2:
      | title              | author              |
      | <bookTitle>        | <bookAuthor>        |
    Then the response status code should be <expectedStatusCode> for put Member2

    Examples:
      | bookTitle         | bookAuthor       | expectedStatusCode |
      |                   | Missing Title 45 | 400                |
      | Missing Author 45 |                  | 400                |
      |                   |                  | 400                |
