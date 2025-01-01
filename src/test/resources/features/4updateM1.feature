@UpdateBookMember1
Feature: Book Update Scenarios by Member1
  Scenario Outline: Book Update by Different User Types
    Given I am logged in as "<username>" with password "<password>" to update as "<userType>" Member1
    When I send a PUT request to "/api/books/<bookId>" with the following data Member1:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode> for put Member1

    Examples:
      | username   | password | userType     | bookId | bookTitle           | bookAuthor         | expectedStatusCode |
      | admin      | password | admin        | 3      | Brave New World 12345   | Aldous Huxley 15   | 200                |
      | user       | password | user         | 3      | The Alchemist    12s4    | Paulo Coelho    15   | 403                |
      | guest      | password | guest        | 3      | The Alchemist    12s3    | Paulo Coelho    15   | 401                |

  Scenario Outline: Book Update by Different validation Types
    Given I am logged in as "<username>" with password "<password>" to update as "<userType>" Member1
    When I send a PUT request to "/api/books/<bookId>" with the following data Member1:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode> for put Member1

    Examples:
      | username   | password | userType     | bookId | bookTitle            | bookAuthor          | expectedStatusCode |
      | admin      | password | admin        |   3    |                      | Aldous Huxley 5asda | 400             |
      | admin      | password | admin        |   3    |      test book       |                     | 400             |
