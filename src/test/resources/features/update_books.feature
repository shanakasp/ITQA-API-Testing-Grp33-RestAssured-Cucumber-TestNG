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
      | admin      | password | admin        | 3      | Brave New World 2s82   | Aldous Huxley 5   | 200                |
      | user       | password | user         | 3      | The Alchemist    s    | Paulo Coelho    5   | 403                |