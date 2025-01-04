@DeleteBooks
Feature: Delete a book

  Scenario: Delete a book by ID as admin
    Given I am logged in to delete as admin user name "admin" password "admin"
    When I send a DELETE request to "/api/books/1"
    Then the response delete status code should be 200
    And the delete response body should contain "Book deleted successfully"

  Scenario: Delete a book by ID as user
    Given I am logged in to delete as user name "user" password "password"
    When I send a DELETE request to "/api/books/1"
    Then the response delete status code should be 401
    And the delete response body should contain "Unauthorized access"
