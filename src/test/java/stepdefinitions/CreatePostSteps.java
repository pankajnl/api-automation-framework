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
        context.setCreatePostRequest(TestDataReader.readTestData(filepath, Posts.class));
    }

    @When("I submit the create post API request")
    public void iSubmitTheCreatePostApiRequest() {
        response = given().spec(Utils.requestSpecification()).body(context.getCreatePostRequest())
                               .when().post(APIResources.CreatePostAPI.getResource());

        context.setCreatePostResponse(response.as(Posts.class));

        response.then().statusCode(201);


    }
    @Then("new post should be created successfully")
    public void newPostCreatedSuccessfully() {
        Validator.validateCommonHeaders(response);
        Validator.validateCreatePostResponse(response);

        SchemaValidator.validateSchema(response, "create_post_response_schema.json");

        Assert.assertEquals(
                context.getCreatePostResponse().getTitle(),
                context.getCreatePostRequest().getTitle(),
                "Title should match request payload"
        );

        Assert.assertEquals(
                context.getCreatePostResponse().getBody(),
                context.getCreatePostRequest().getBody(),
                "Body should match request payload"
        );
        Assert.assertEquals(
                context.getCreatePostResponse().getUserId(),
                context.getCreatePostRequest().getUserId(),
                "UserId should match request payload"
        );
        Assert.assertNotNull(context.getCreatePostResponse().getId(), "Generated id should not be null");
        Assert.assertTrue(context.getCreatePostResponse().getId() > 0, "Generated id should be greater than 0");
    }
}
