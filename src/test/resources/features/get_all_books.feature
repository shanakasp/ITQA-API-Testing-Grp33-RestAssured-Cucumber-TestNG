@GetAllBooks
Feature: Retrieve Books
  As an admin user, I want to retrieve a list of books so that I can manage them.

  Scenario Outline: Get the list of books with different admin credentials Get
    Given I am an admin user with username "<username>" and password "<password>" Get
    When I send a GET request to "/api/books" Get
    Then the response status code should be <expectedStatusCode> Get
    And the response should <responseValidation> Get

    Examples:
      | username | password | expectedStatusCode | responseValidation      |
      | admin    | password | 200                | contain a list of books |
      | admin2   | password2| 401                | be empty or invalid     |
      | admin3   | password3| 401                | be empty or invalid     |