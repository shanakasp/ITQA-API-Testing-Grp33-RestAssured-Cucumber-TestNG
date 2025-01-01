@UpdateBook
Feature: Book Update Scenarios
  Scenario Outline: Book Update by Different User Types
    Given I am logged in as "<username>" with password "<password>" to update as "<userType>"
    When I send a PUT request to "/api/books/<bookId>" with the following data:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode> for put

    Examples:
      | username   | password | userType     | bookId | bookTitle           | bookAuthor         | expectedStatusCode |
      | admin      | password | admin        | 1      | Brave New World 12345   | Aldous Huxley 15   | 200                |
      | user       | password | user         | 1      | The Alchemist    12s4    | Paulo Coelho    15   | 403                |
      | guest       | password | guest         | 1      | The Alchemist    12s3    | Paulo Coelho    15   | 401                |

  Scenario Outline: Book Update by Different validation Types
    Given I am logged in as "<username>" with password "<password>" to update as "<userType>"
    When I send a PUT request to "/api/books/<bookId>" with the following data:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode> for put

    Examples:
      | username   | password | userType     | bookId | bookTitle            | bookAuthor          | expectedStatusCode |
      | admin      | password | admin        |   1    |                      | Aldous Huxley 5asda | 400             |
      | admin      | password | admin        |   1    |      test book       |                     | 400             |
      | user       | password | user         | 1      | The Alchemist    s2  |                     | 403             |
      | guest      | password | guest         | 1      | The Alchemist    s2  |                     | 200           |
      | guest      | password | guest         | 1      |                      |        author abc    | 200            |

  Scenario Outline: Update Book with Validation Errors
    Given I am logged in as "admin" with password "password" to update as admin
    When I send a PUT request to "/api/books/3" with the following data:
      | title              | author              |
      | <bookTitle>        | <bookAuthor>        |
    Then the response status code should be <expectedStatusCode> for put

    Examples:
      | bookTitle        | bookAuthor      | expectedStatusCode |
      |                  | Missing Title 45   | 400                |
      | Missing Author 45  |                 | 400                |
      |                  |                 | 400                |