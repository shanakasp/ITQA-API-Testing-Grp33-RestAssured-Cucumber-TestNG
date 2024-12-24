package com.APIAutomation.stepdefinitions.get;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class getBookById {
    private Response response;
    private String username;
    private String password;

    @Given("I am an user with username {string} and password {string} to log get book get")
    public void i_am_an_user_with_username_and_password(String username, String password) {
        // Store username and password for Basic Auth
        this.username = username;
        this.password = password;
        System.out.println("Trying to authenticate with Username: " + username + " and Password: " + password);
    }

    @When("I send a GET request for book ID {int} get")
    public void i_send_a_get_request_for_book_id(Integer bookId) {
        // Send a GET request with Basic Auth
        String endpoint = "/api/books/" + bookId;
        RestAssured.baseURI = "http://localhost:7081";
        response = RestAssured.given()
                .auth()
                .basic(username, password)
                .get(endpoint);
    }

    @Then("the response status code should be {int} get")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode.intValue(), "Unexpected response status code");
    }

    @Then("the response should contain book details get")
    public void the_response_should_contain_book_details() {
        // If valid credentials and book exists, check for book details in the response
        if (response.getStatusCode() == 200) {
            String book = response.jsonPath().getString("$");
            Assert.assertNotNull(book, "Book details should not be null");
            System.out.println("Book details retrieved: " + book);
        } else {
            Assert.fail("Unable to retrieve book details.");
        }
    }

    @Then("the response should indicate book not found get")
    public void the_response_should_indicate_book_not_found() {
        // Check if the response body indicates the book is not found
        if (response.getStatusCode() == 404) {
            String errorMessage = response.body().asString();
            Assert.assertTrue(errorMessage.contains("Book not found"), "Response body should indicate book not found");
            System.out.println("Book not found: " + errorMessage);
        } else {
            Assert.fail("Expected 404 Not Found, but got status code: " + response.getStatusCode());
        }
    }

    @Then("the response should be unauthorized get")
    public void the_response_should_be_unauthorized() {
        // Handle unauthorized access
        if (response.getStatusCode() == 401) {
            String errorMessage = response.body().asString();
            Assert.assertTrue(errorMessage.contains("Unauthorized") || errorMessage.isEmpty(),
                    "Response should indicate unauthorized access or be empty");
            System.out.println("Invalid credentials provided: Status 401.");
        } else {
            Assert.fail("Expected 401 Unauthorized, but got status code: " + response.getStatusCode());
        }
    }

    @Then("the response should not authorized get")
    public void the_response_should_not_authorized_get() {
        // Handle unauthorized access
        if (response.getStatusCode() == 401) {
            String errorMessage = response.body().asString();
            Assert.assertTrue(errorMessage.contains("Unauthorized") || errorMessage.isEmpty(),
                    "Response should indicate unauthorized access or be empty");
            System.out.println("Invalid credentials provided: Status 401.");
        } else {
            Assert.fail("Expected 401 Unauthorized, but got status code: " + response.getStatusCode());
        }
    }
}