package stepdefinitions;

import context.TestContext;
import helpers.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.Posts;

import static io.restassured.RestAssured.given;

public class CreatePostSteps {
    private final TestContext context;
    private Response response;

    public CreatePostSteps(TestContext context) {
        this.context = context;
    }

    @Given("create post request data is loaded from {string}")
    public void createPostRequestDataIsLoadedFrom(String filepath) {
        context.createPostRequest = TestDataReader.readTestData(filepath, Posts.class);
    }

    @When("I submit the create post API request")
    public void iSubmitTheCreatePostApiRequest() {
        response = given().spec(Utils.requestSpecification()).body(context.createPostRequest)
                               .when().post(APIResources.CreatePostAPI.getResource());

        context.createPostResponse = response.as(Posts.class);

        response.then().statusCode(201);

        Validator.validateCommonHeaders(response);
        Validator.validateCreatePostResponse(response);

        SchemaValidator.validateSchema(response, "create_post_response_schema.json");
    }
    @Then("new post should be created successfully")
    public void newPostCreatedSuccessfully() {
        Assert.assertEquals(
                context.createPostResponse.getTitle(),
                context.createPostRequest.getTitle(),
                "Title should match request payload"
        );

        Assert.assertEquals(
                context.createPostResponse.getBody(),
                context.createPostRequest.getBody(),
                "Body should match request payload"
        );
        Assert.assertEquals(
                context.createPostResponse.getUserId(),
                context.createPostRequest.getUserId(),
                "Body should match request payload"
        );
    }
}
