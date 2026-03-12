package context;

import pojo.User;

import java.util.ArrayList;
import java.util.List;

//This class is used to store data that needs to be shared across different step definition classes during the execution of a test scenario.
public class TestContext {
    public User currentUser;
    public Integer userId;
    public List<Integer> postIds = new ArrayList<>();
    public List<String> emailsInComments = new ArrayList<>();

    public void clear() {
        currentUser = null;
        userId = null;
        postIds.clear();
        emailsInComments.clear();
    }
}
