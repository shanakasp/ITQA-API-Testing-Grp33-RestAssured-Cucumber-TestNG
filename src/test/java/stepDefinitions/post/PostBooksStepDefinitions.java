package stepDefinitions.post;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.cucumber.java.Before;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class PostBooksStepDefinitions {
    private String baseUrl = "http://localhost:7081/api/books";
    private Response response;
    private String username;
    private String password;
    private String userType;
    private Map<String, String> bookData;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:7081";
        RestAssured.basePath = "";
    }

    @Given("I am logged in as {string} with password {string} to post as {word}")
    public void loginUser(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;  // Capture the userType from the feature file
    }

    @When("I send a POST request to {string} with the following data:")
    public void sendPostRequest(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        // Extract book data from the data table
        bookData = dataTable.asMaps().get(0);

        // Determine authentication based on userType
        if ("unauthorized".equals(userType)) {
            // No authentication for unauthorized user
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(bookData)
                    .when()
                    .post(endpoint);
        } else {
            // Basic auth for admin and user
            response = RestAssured.given()
                    .auth()
                    .basic(username, password)
                    .contentType(ContentType.JSON)
                    .body(bookData)
                    .when()
                    .post(endpoint);
        }
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode,
                "Unexpected status code. Response body: " + response.getBody().asString());
    }
}
