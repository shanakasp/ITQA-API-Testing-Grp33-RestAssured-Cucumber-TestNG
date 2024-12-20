package com.APIAutomation.stepdefinitions.update;


import io.restassured.RestAssured;

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


}