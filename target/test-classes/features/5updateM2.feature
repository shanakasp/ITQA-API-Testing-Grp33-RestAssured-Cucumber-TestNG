@UpdateBookMember2
Feature: Book Update Scenarios by Member2
  Scenario Outline: Book Update by Different validation Types
    Given I am logged in as "<username>" with password "<password>" to update as "<userType>" Member1
    When I send a PUT request to "/api/books/<bookId>" with the following data Member1:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode> for put Member1

    Examples:
      | username   | password | userType     | bookId | bookTitle             | bookAuthor          | expectedStatusCode |
      | user       | password | user         | 3      | The Alchemist    s2   |                     | 401                |
      | guest      | password | guest        | 3      | The Alchemist    s2   |                     | 401                |
      | guest      | password | guest        | 3      |                       |        author abc   | 401                |

  Scenario Outline: Update Book with Validation Errors
    Given I am logged in as "admin" with password "password" to update as admin Member1
    When I send a PUT request to "/api/books/3" with the following data Member1:
      | title              | author              |
      | <bookTitle>        | <bookAuthor>        |
    Then the response status code should be <expectedStatusCode> for put Member1

    Examples:
      | bookTitle          | bookAuthor         | expectedStatusCode |
      |                    | Missing Title 45   | 400                |
      | Missing Author 45  |                    | 400                |
      |                    |                    | 400                |