@CreateBook
Feature: Book Creation Scenarios

  Scenario Outline: Book Creation by Different User Types
    Given I am logged in as "<username>" with password "<password>" to post as "<userType>"
    When I send a POST request to "/api/books" with the following data:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode>
    Examples:
      | username   | password | userType     | bookTitle         | bookAuthor           | expectedStatusCode |
      | admin      | password | admin        | The Great Gatsby  | F. Scott Fitzgerald  | 201                |
      | admin      | password | admin        | The Great Gatsby2 | F. Scott Fitzgerald  | 201                |
      | user       | password | user         | 1984    12        | George Orwell        | 201                |
      | guest      | password | unauthorized | Moby Dick         | Herman Melville      | 401                |
