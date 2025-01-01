package com.APIAutomation.stepdefinitions.update;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.cucumber.java.Before;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;


public class BookUpdateM1StepDefinitions {
    private String baseUrl = "http://localhost:7081/api/books";
    private Response response;
    private String username;
    private String password;
    private Map<String, Object> bookData;
    private String bookId;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:7081";
        RestAssured.basePath = "";
    }
    @Given("I am logged in as {string} with password {string} to update as {word} Member1")
    public void loginUser(String username, String password, String userType) {
        this.username = username;
        this.password = password;
    }
    @When("I send a PUT request to {string} with the following data Member1:")
    public void sendPutRequest(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        // Extract book data from the data table
        Map<String, String> inputData = dataTable.asMaps().get(0);

        // Extract book ID from the endpoint
        bookId = endpoint.substring(endpoint.lastIndexOf('/') + 1);

        // Create book data map with all required fields
        bookData = new HashMap<>();
        bookData.put("id", Integer.parseInt(bookId));
        bookData.put("title", inputData.getOrDefault("title", ""));  // Use empty string if not provided
        bookData.put("author", inputData.getOrDefault("author", "")); // Use empty string if not provided

        // Determine authentication based on username
        if ("guest".equals(username)) {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(bookData)
                    .log().body()  // Add request logging
                    .when()
                    .put(endpoint);
        } else {
            response = RestAssured.given()
                    .auth()
                    .basic(username, password)
                    .contentType(ContentType.JSON)
                    .body(bookData)
                    .log().body()  // Add request logging
                    .when()
                    .put(endpoint);
        }

        // Print response for debugging
        System.out.println("Request Body: " + bookData);
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());
    }

    @Then("the response status code should be {int} for put Member1")
    public void verifyStatusCode(int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode,
                "Unexpected status code. Response body: " + response.getBody().asString());
    }

}