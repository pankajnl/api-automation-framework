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

    public CommentsEmailValidationSteps(TestContext context) {
        this.context = context;
    }

    @Given("a username is {string}")
    public void setUsername(String username) {
        Response response = given().spec(Utils.requestSpecification()).queryParam("username",username)
                              .when().get(APIResources.GetUserAPI.getResource());

        response.then().statusCode(200);

        Validator.validateCommonHeaders(response);

        Validator.validateUserResponse(response);

        SchemaValidator.validateSchema(response, "user_schema.json");

        User[] users = response.as(User[].class);
        context.currentUser = users[0];
        context.userId = context.currentUser.getId();
    }
    @When("I fetch posts for that user")
    public void fetchPostForUser() {
        Response response = given().spec(Utils.requestSpecification()).queryParam("userId",context.userId)
                               .when().get(APIResources.GetPostAPI.getResource());

        response.then().statusCode(200);

        Validator.validateCommonHeaders(response);

        Validator.validatePostsResponse(response);

        SchemaValidator.validateSchema(response, "posts_schema.json");

        Posts[] posts = response.as(Posts[].class);
        context.postIds.clear();
        for (Posts post : posts) {
            context.postIds.add(post.getId());
        }
    }
    @When("I fetch comments for each post")
    public void fetchCommentsForEachPost() {
        context.emailsInComments.clear();
        for(Integer postId : context.postIds) {
            Response response = given().spec(Utils.requestSpecification()).queryParam("postId",postId)
                                         .when().get(APIResources.GetCommentApi.getResource());

            response.then().statusCode(200);

            Validator.validateCommonHeaders(response);

            Validator.validateCommentsResponse(response);

            SchemaValidator.validateSchema(response, "comment_schema.json");

            Comments[] comments = response.as(Comments[].class);
            if (comments != null && comments.length > 0) {
                for (Comments comment : comments) {
                    context.emailsInComments.add(comment.getEmail());
                }
            }
        }
    }
    @Then("all emails in comments should be valid")
    public void validateEmailInComments() {
        for(String email : context.emailsInComments) {
            Assert.assertNotNull(email, "Email should not be null");
            Assert.assertFalse(email.trim().isEmpty(), "Email should not be empty");
            Assert.assertTrue(Validator.validateEmail(email),
                    "Email '" + email + "' does not have a valid format");
        }
    }
}
