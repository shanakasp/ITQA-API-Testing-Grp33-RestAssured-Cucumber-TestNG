@UpdateBook
  Feature: Book Update Scenarios
    Scenario Outline:Book Update by Different validation types
      Given I am logged in as "<username>" with password "<password>" to update as "<userType>"
      When I send a PUT request to "/api/books/<bookId> with the following data:
      |title|author|<bookTitle>|<bookAuthor>|
      Then the response delete status code should be <expectedStatusCode> for put



      Examples:
        | username |password|userType|bookId|bookTitle|bookAuthor|expectedStatusCode|
