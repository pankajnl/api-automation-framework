package stepdefinitions;

import context.TestContext;
import helpers.APIResources;
import helpers.SchemaValidator;
import helpers.Utils;
import helpers.Validator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.Comments;
import pojo.Posts;
import pojo.User;

import static io.restassured.RestAssured.given;

// This class contains step definitions for feature file emails_in_comments_validation.feature.
public class CommentsEmailValidationSteps {
    private final TestContext context;
    private Response response;

    public CommentsEmailValidationSteps(TestContext context) {
        this.context = context;
    }

    @Given("a username is {string}")
    public void setUsername(String username) {
        response = given().spec(Utils.requestSpecification()).queryParam("username",username)
                              .when().get(APIResources.GetUserAPI.getResource());

        response.then().statusCode(200);

        Validator.validateCommonHeaders(response);

        Validator.validateUserResponse(response);

        SchemaValidator.validateSchema(response, "user_schema.json");

        User[] users = response.as(User[].class);
        context.setCurrentUser(users[0]);
        context.setUserId(context.getCurrentUser().getId());
    }
    @When("I fetch posts for that user")
    public void fetchPostForUser() {
        response = given().spec(Utils.requestSpecification()).queryParam("userId", context.getUserId())
                               .when().get(APIResources.GetPostAPI.getResource());

        response.then().statusCode(200);

        Validator.validateCommonHeaders(response);

        Validator.validatePostsResponse(response);

        SchemaValidator.validateSchema(response, "posts_schema.json");

        Posts[] posts = response.as(Posts[].class);
        context.getPostIds().clear();
        for (Posts post : posts) {
            context.getPostIds().add(post.getId());
        }
    }
    @When("I fetch comments for each post")
    public void fetchCommentsForEachPost() {
        context.getEmailsInComments().clear();
        for(Integer postId : context.getPostIds()) {
            response = given().spec(Utils.requestSpecification()).queryParam("postId",postId)
                                         .when().get(APIResources.GetCommentApi.getResource());

            response.then().statusCode(200);

            Validator.validateCommonHeaders(response);

            Validator.validateCommentsResponse(response);

            SchemaValidator.validateSchema(response, "comment_schema.json");

            Comments[] comments = response.as(Comments[].class);
            if (comments != null && comments.length > 0) {
                for (Comments comment : comments) {
                    context.getEmailsInComments().add(comment.getEmail());
                }
            }
        }
    }
    @Then("all emails in comments should be valid")
    public void validateEmailInComments() {
        for(String email : context.getEmailsInComments()) {
            Assert.assertNotNull(email, "Email should not be null");
            Assert.assertFalse(email.trim().isEmpty(), "Email should not be empty");
            Assert.assertTrue(Validator.validateEmail(email),
                    "Email '" + email + "' does not have a valid format");
        }
    }
}
