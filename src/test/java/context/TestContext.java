package context;

import pojo.Posts;
import pojo.User;

import java.util.ArrayList;
import java.util.List;

//This class is used to store data that needs to be shared across different step definition classes during the execution of a test scenario.
public class TestContext {
    private User currentUser;
    private Integer userId;
    private List<Integer> postIds = new ArrayList<>();
    private List<String> emailsInComments = new ArrayList<>();
    private Posts createPostRequest;
    private Posts createPostResponse;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getPostIds() {
        return postIds;
    }

    public List<String> getEmailsInComments() {
        return emailsInComments;
    }

    public Posts getCreatePostRequest() {
        return createPostRequest;
    }

    public void setCreatePostRequest(Posts createPostRequest) {
        this.createPostRequest = createPostRequest;
    }

    public Posts getCreatePostResponse() {
        return createPostResponse;
    }

    public void setCreatePostResponse(Posts createPostResponse) {
        this.createPostResponse = createPostResponse;
    }


    public void clear() {
        currentUser = null;
        userId = null;
        postIds.clear();
        emailsInComments.clear();
        createPostRequest = null;
        createPostResponse = null;
    }
}
