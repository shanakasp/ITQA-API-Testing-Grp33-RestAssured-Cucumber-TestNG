package stepDefinitions.update;

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

    @When("I send a PUT request to {string} with the following data:")
    public void sendPutRequest(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        // Extract book data from the data table
        Map<String, String> inputData = dataTable.asMaps().get(0);

        // Extract book ID from the endpoint
        bookId = endpoint.substring(endpoint.lastIndexOf('/') + 1);

        // Create book data map with ID
        bookData = new HashMap<>();
        bookData.put("id", Integer.parseInt(bookId));

        // Only add title and author if they are not empty
        if (inputData.get("title") != null && !inputData.get("title").isEmpty()) {
            bookData.put("title", inputData.get("title"));
        }
        if (inputData.get("author") != null && !inputData.get("author").isEmpty()) {
            bookData.put("author", inputData.get("author"));
        }

        // Determine authentication based on username
        if ("guest".equals(username)) {
            // No authentication for unauthorized user
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(bookData)
                    .when()
                    .put(endpoint);
        } else {
            // Basic auth for admin and user
            response = RestAssured.given()
                    .auth()
                    .basic(username, password)
                    .contentType(ContentType.JSON)
                    .body(bookData)
                    .when()
                    .put(endpoint);
        }

        // Print response for debugging
        System.out.println("Request Body: " + bookData);
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());
    }

    @Then("the response status code should be {int} for put")
    public void verifyStatusCode(int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode,
                "Unexpected status code. Response body: " + response.getBody().asString());
    }
}