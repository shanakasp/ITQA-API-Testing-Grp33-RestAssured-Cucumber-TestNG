@UpdateBookMember2
Feature: Book Update Scenarios by Member2
  Scenario Outline: Book Update by Different validation Types
    Given I am logged in as "<username>" with password "<password>" to update as "<userType>" Member1
    When I send a PUT request to "/api/books/<bookId>" with the following data Member1:
      | title              | author                |
      | <bookTitle>        | <bookAuthor>          |
    Then the response status code should be <expectedStatusCode> for put Member1

    Examples:
      | username   | password | userType     | bookId | bookTitle            | bookAuthor          | expectedStatusCode |
      | user       | password | user         | 1      | The Alchemist    s2  |                     | 403             |
      | guest      | password | guest         | 1      | The Alchemist    s2  |                     | 401           |
      | guest      | password | guest         | 1      |                      |        author abc    | 401            |