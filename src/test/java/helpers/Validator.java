package helpers;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.*;

import java.util.List;

public class Validator {
    //This method validates the email format using a regular expression.
    public static boolean validateEmail(String email) {
        // This method validates the email format using a regular expression.
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    // This method validates the user response by checking that the response is not empty and that all required fields are present and valid.
    public static void validateUserResponse(Response response) {
        List<User> users = response.as(new TypeRef<List<User>>() {});

        Assert.assertFalse(users.isEmpty(), "User response should not be empty");

        User user = users.get(0);
        Assert.assertTrue(user.getId() > 0, "User id should be greater than 0");
        Assert.assertNotNull(user.getName(), "User name should not be null");
        Assert.assertNotNull(user.getUsername(), "Username should not be null");
        Assert.assertNotNull(user.getEmail(), "Email should not be null");

        Address address = user.getAddress();
        Assert.assertNotNull(address, "Address should not be null");
        Assert.assertNotNull(address.getStreet(), "Street should not be null");
        Assert.assertNotNull(address.getSuite(), "Suite should not be null");
        Assert.assertNotNull(address.getCity(), "City should not be null");
        Assert.assertNotNull(address.getZipcode(), "Zipcode should not be null");

        Geo geo = address.getGeo();
        Assert.assertNotNull(geo, "Geo should not be null");
        Assert.assertNotNull(geo.getLat(), "Latitude should not be null");
        Assert.assertNotNull(geo.getLng(), "Longitude should not be null");

        Assert.assertNotNull(user.getPhone(), "Phone should not be null");
        Assert.assertNotNull(user.getWebsite(), "Website should not be null");

        Company company = user.getCompany();
        Assert.assertNotNull(company, "Company should not be null");
        Assert.assertNotNull(company.getName(), "Company name should not be null");
        Assert.assertNotNull(company.getCatchPhrase(), "Company catchPhrase should not be null");
        Assert.assertNotNull(company.getBs(), "Company bs should not be null");
    }

    // This method validates the posts response by checking that the response is not empty and that all required fields are present and valid.
    public static void validatePostsResponse(Response response) {
        List<Posts> posts = response.as(new TypeRef<List<Posts>>() {});

        Assert.assertFalse(posts.isEmpty(), "Posts response should not be empty");

        Posts post = posts.get(0);
        Assert.assertTrue(post.getId() > 0, "Post id should be greater than 0");
        Assert.assertTrue(post.getUserId() > 0, "User id should be greater than 0");
        Assert.assertNotNull(post.getTitle(), "Post title should not be null");
        Assert.assertNotNull(post.getBody(), "Post body should not be null");
    }

    // This method validates the comments response by checking that the response is not empty and that all required fields are present and valid.
    public static void validateCommentsResponse(Response response) {
        List<Comments> comments = response.as(new TypeRef<List<Comments>>() {});

        Assert.assertFalse(comments.isEmpty(), "Comments response should not be empty");

        Comments comment = comments.get(0);
        Assert.assertTrue(comment.getId() > 0, "Comment id should be greater than 0");
        Assert.assertTrue(comment.getPostId() > 0, "Post id should be greater than 0");
        Assert.assertNotNull(comment.getName(), "Comment name should not be null");
        Assert.assertNotNull(comment.getEmail(), "Comment email should not be null");
        Assert.assertNotNull(comment.getBody(), "Comment body should not be null");
    }

    public static void validateCreatePostResponse(Response response) {
        Posts post = response.as(Posts.class);

        Assert.assertTrue(post.getId() > 0, "Post id should be greater than 0");
        Assert.assertTrue(post.getUserId() > 0, "User id should be greater than 0");
        Assert.assertNotNull(post.getTitle(), "Post title should not be null");
        Assert.assertNotNull(post.getBody(), "Post body should not be null");
    }

    // This method validates common headers in the response, such as Content-Type, Connection, and Date.
    public static void validateCommonHeaders(Response response) {

        String contentType = response.getHeader("Content-Type");
        Assert.assertNotNull(contentType, "Content-Type header should be present");
        Assert.assertTrue(
                contentType.contains("application/json"),
                "Content-Type should be application/json"
        );

        String connection = response.getHeader("Connection");
        Assert.assertNotNull(connection, "Connection header should be present");

        String date = response.getHeader("Date");
        Assert.assertNotNull(date, "Date header should be present");
    }
}
