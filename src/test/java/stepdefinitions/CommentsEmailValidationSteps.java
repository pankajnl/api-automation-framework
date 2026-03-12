package stepdefinitions;

import helpers.APIResources;
import helpers.Utils;
import helpers.Validator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pojo.Comments;
import pojo.Posts;
import pojo.User;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

// This class contains step definitions for feature file emails_in_comments_validation.feature.
public class CommentsEmailValidationSteps {
    private final TestContext context;

    public CommentsEmailValidationSteps(TestContext context) {
        this.context = context;
    }

    @Given("a username is {string}")
    public void setUsername(String username) {
        User[] users = given().spec(Utils.requestSpecification()).queryParam("username",username)
                              .when().get(APIResources.GetUserAPI.getResource())
                              .then().statusCode(200).extract().as(User[].class);
        Assert.assertNotNull(users, "Users array should not be null");
        Assert.assertTrue(users.length > 0, "User with username '" + username + "' should exist");
        context.currentUser = users[0];
        context.userId = context.currentUser.getId();
    }
    @When("I fetch posts for that user")
    public void fetchPostForUser() {
        Posts[] posts = given().spec(Utils.requestSpecification()).queryParam("userId",context.userId)
                               .when().get(APIResources.GetPostAPI.getResource())
                               .then().statusCode(200).extract().as(Posts[].class);
        Assert.assertNotNull(posts, "Posts array should not be null");
        Assert.assertTrue(posts.length > 0, "User should have at least one post to validate comments");
        context.postIds.clear();
        for (Posts post : posts) {
            context.postIds.add(post.getId());
        }
    }
    @When("I fetch comments for each post")
    public void fetchCommentsForEachPost() {
        context.emailsInComments.clear();
        for(Integer postId : context.postIds) {
            Comments[] comments = given().spec(Utils.requestSpecification()).queryParam("postId",postId)
                                         .when().get(APIResources.GetCommentApi.getResource())
                                         .then().statusCode(200).extract().as(Comments[].class);
            if (comments != null && comments.length > 0) {
                for (Comments comment : comments) {
                    context.emailsInComments.add(comment.getEmail());
                }
            }
        }
        Assert.assertFalse(context.emailsInComments.isEmpty(), "At least one comment with email should exist for validation");
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
