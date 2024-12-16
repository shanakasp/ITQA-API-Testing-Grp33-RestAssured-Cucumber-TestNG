package com.APIAutomation.stepdefinitions.delete;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class BookDeletionStepDefinition {
    private static final String BASE_URL = "http://localhost:7081";
    private Response response;
    private String username;
    private String password;

    @Given("I am logged in to delete as admin user name {string} password {string}")
    public void login_as_admin(String username, String password) {
        // Configure base URL
        RestAssured.baseURI = BASE_URL;

        // Store admin credentials
        this.username = username;
        this.password = password;
    }

    @Given("I am logged in to delete as user name {string} password {string}")
    public void login_as_user(String username, String password) {
        // Configure base URL
        RestAssured.baseURI = BASE_URL;

        // Store user credentials
        this.username = username;
        this.password = password;
    }

    @Given("I am logged in to delete not existing as user name {string} password {string}")
    public void login_as_user_for_non_existing_book(String username, String password) {
        // Configure base URL
        RestAssured.baseURI = BASE_URL;

        // Store user credentials
        this.username = username;
        this.password = password;
    }

    @When("I send a DELETE request to {string}")
    public void send_delete_request(String endpoint) {
        // Send DELETE request with Basic Authentication
        response = given()
                .auth()
                .basic(username, password)
                .when()
                .delete(endpoint)
                .then()
                .extract().response();
    }

    @Then("the response delete status code should be {int}")
    public void verify_status_code(int expectedStatusCode) {
        assertEquals("Delete request status code does not match",
                expectedStatusCode, response.getStatusCode());
    }

    @Then("the delete response body should contain {string}")
    public void verify_response_body(String expectedMessage) {
        String responseBody = response.getBody().asString();
        assertTrue("Response body does not contain expected message",
                responseBody.contains(expectedMessage));
    }
}