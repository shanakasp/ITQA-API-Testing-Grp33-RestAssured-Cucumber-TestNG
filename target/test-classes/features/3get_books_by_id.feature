@GetBookByID
Feature: Retrieve Book by ID
  As an user, I want to retrieve a book by its ID so that I can view or manage it.

  Scenario Outline: Get the details of a book by its ID with different credentials get
    Given I am an user with username "<username>" and password "<password>" to log get book get
    When I send a GET request for book ID <bookId> get
    Then the response status code should be <expectedStatusCode> get
    And the response should <responseValidation> get

    Examples:
      | username | password  | bookId | expectedStatusCode | responseValidation             |
      | admin    | password  | 1      | 200                | contain book details           |
      | user    | password  | 1      | 200                | contain book details           |
      | admin    | password  | 100    | 404                | indicate book not found        |
      | admin2   | password2 | 99     | 401                | not authorized           |