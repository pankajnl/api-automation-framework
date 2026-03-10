package helpers;

/*Enum is a special class in java which has a fixed set of constants.
In this case, we are using an enum to define the API resources (endpoints) that we will be testing.
Each constant in the enum represents a specific API endpoint, and we can easily retrieve the corresponding resource path using the getResource() method.
This approach helps to keep our code organized and maintainable, as we can centralize the API endpoints in one place and avoid hardcoding them throughout our test cases
*/
public enum APIResources {
    GetUserAPI("/users"),
    GetPostAPI("/posts"),
    GetCommentApi("/comments");

    private String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
