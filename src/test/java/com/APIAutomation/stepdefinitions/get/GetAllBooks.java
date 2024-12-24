package com.APIAutomation.stepdefinitions.get;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetAllBooks {
    private Response response;
    private String username;
    private String password;
    private Integer bookId;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:7081";
    }

    @Given("I am an admin user with username {string} and password {string} Get")
    public void setCredentialsGet(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Given("a book with ID {int} exists in the system Get")
    public void verifyBookExistsGet(Integer bookId) {
        this.bookId = bookId;
    }

    @When("I send a GET request to {string} Get")
    public void i_send_a_get_request_to_Get(String endpoint) {
        // Extract book ID from the endpoint if present
        Pattern pattern = Pattern.compile("/api/books/(\\d+)");
        Matcher matcher = pattern.matcher(endpoint);

        if (matcher.find()) {
            // If endpoint contains a book ID, it's a single book request
            bookId = Integer.parseInt(matcher.group(1));
            response = RestAssured.given()
                    .auth()
                    .basic(username, password)
                    .get(endpoint);
        } else {
            // Otherwise, it's a list of books request
            response = RestAssured.given()
                    .auth()
                    .basic(username, password)
                    .get(endpoint);
        }
    }

    @Then("the response status code should be {int} Get")
    public void validateStatusCodeGet(Integer expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode.intValue(),
                "Unexpected response status code. Response body: " + response.getBody().asString());
    }

    @Then("the response should contain a list of books Get")
    public void validateBooksListGet() {
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve books");
        List<Map<String, Object>> books = response.jsonPath().getList("$");
        Assert.assertFalse(books.isEmpty(), "Books list is empty");

        // Print books for debugging
        books.forEach(book -> System.out.println("Book: " + book));
    }

    @Then("the response should be empty or invalid Get")
    public void validateEmptyResponseGet() {
        Assert.assertTrue(
                response.getStatusCode() == 401,
                "Expected unauthorized access"
        );
        Assert.assertTrue(
                response.getBody().asString().isEmpty(),
                "Response body should be empty for unauthorized users"
        );
    }

    @Then("the response should contain valid book details Get")
    public void validateBookDetailsGet() {
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve book details");

        // Validate basic book structure
        Map<String, Object> book = response.jsonPath().getMap("$");
        Assert.assertNotNull(book, "Book details should not be null");
        Assert.assertTrue(book.containsKey("id"), "Book should have an ID");
        Assert.assertTrue(book.containsKey("title"), "Book should have a title");
        Assert.assertTrue(book.containsKey("author"), "Book should have an author");

        // Print book details for debugging
        System.out.println("Retrieved Book Details: " + book);
    }

    @Then("the response should be forbidden Get")
    public void validateForbiddenResponseGet() {
        Assert.assertEquals(response.getStatusCode(), 403, "Expected forbidden access");
    }

    @Then("the response should be unauthorized Get")
    public void validateUnauthorizedResponseGet() {
        Assert.assertEquals(response.getStatusCode(), 401, "Expected unauthorized access");
    }

    @Then("the response should show book not found Get")
    public void validateBookNotFoundResponseGet() {
        Assert.assertEquals(response.getStatusCode(), 404, "Expected book not found");
    }
}