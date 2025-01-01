package stepDefinitions.get;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.Assert;
import java.util.Map;

public class GetBookById {
    private Response response;
    private String username;
    private String password;

    @Given("I am an user with username {string} and password {string} getbyID")
    public void iAmAnUserWithUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
        System.out.println("Authenticating with Username: " + username + " and Password: " + password);
    }

    @When("I send a GET request for book ID {int} getbyID")
    public void iSendAGetRequestForBookId(Integer bookId) {
        String endpoint = "/api/books/" + bookId;
        RestAssured.baseURI = "http://localhost:7081";
        response = RestAssured.given()
                .auth()
                .basic(username, password)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .get(endpoint);

        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Response Status Code: " + response.getStatusCode());
    }

    @Then("the response status code should be {int} getbyID")
    public void theResponseStatusCodeShouldBe(Integer expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode.intValue(),
                "Unexpected response status code");
    }

    @Then("the response should contain: getbyID")
    public void theResponseShouldContain(Map<String, String> expectedFields) {
        expectedFields.forEach((field, expectedValue) -> {
            String actualValue = response.jsonPath().getString(field);
            Assert.assertEquals(actualValue, expectedValue, "Unexpected value for field: " + field);
        });
    }
}
