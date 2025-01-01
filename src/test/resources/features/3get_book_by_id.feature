@GetBookByID
Feature: Retrieve Book by ID
  As a user, I want to retrieve a book by its ID so that I can view its details

  Scenario Outline: Get book details with different credentials and scenarios getbyID
    Given I am an user with username "<username>" and password "<password>" getbyID
    When I send a GET request for book ID <bookId> getbyID
    Then the response status code should be <expectedStatusCode> getbyID

    Examples:
      | username | password  | bookId | expectedStatusCode |
      | admin    | password  | 1      | 200               |
      | user     | password  | 1      | 200               |
      | admin    | password  | 100    | 404               |
      | admin2   | password2 | 99     | 401               |