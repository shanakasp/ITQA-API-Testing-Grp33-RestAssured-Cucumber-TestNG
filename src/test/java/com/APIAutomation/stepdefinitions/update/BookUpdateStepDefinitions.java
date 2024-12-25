package com.APIAutomation.stepdefinitions.update;


import io.restassured.RestAssured;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import io.cucumber.java.Before;


import java.util.Map;



public class BookUpdateStepDefinitions {
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
    @Given("I am logged in as {string} with password {string} to update as {word}")
    public void loginUser(String username, String password, String userType) {
        this.username = username;
        this.password = password;
    }




}
