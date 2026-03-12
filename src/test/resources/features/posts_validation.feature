@posts_validation @Todo
Feature: Validate response details for posts api endpoint
  @positive_flow
  Scenario: Retrieve posts details when valid userId is provided
    Given a userId is 9
    When I fetch posts for that user
    Then the response should contain the fields
      | userId |
      | id |
      | title |
      | body |

  @positive_flow
  Scenario: Posts should belongs to the user when valid userId is provided
    Given a userId is 9
    When I fetch posts for that user
    Then all posts in the response should have userId as 9

  @positive_flow
  Scenario: No posts should be returned when user has no posts
    Given a userId is 11
    When I fetch posts for that user
    Then the response should contain no posts

  @positive_flow
  Scenario: Posts should contain valid post ids when valid userId is provided
    Given a userId is 9
    When I fetch posts for that user
    Then each posts in the response should contain a valid id

  @performance
  Scenario: Validate response time for posts API
    Given a userId is 9
    When I fetch posts for that user
    Then the response time should be less than 2 seconds

  @performance
  Scenario: Validate API stability under repeated posts requests
    Given a userId is 9
    When I request posts details for userId 50 times
    Then all responses should return status code 200
    And all responses should maintain a valid structure

  @negative_flow
  Scenario: No posts should be returned when invalid userId is provided
    Given a userId is 10000
    When I fetch posts for that user
    Then the response should contain no posts

  @negative_flow
  Scenario: No post should be returned when userId is non numeric
    Given a userId is "test"
    When I fetch posts for that user
    Then the response should contain no posts

  @negative_flow
  Scenario: No post should be returned when userId is empty
    Given a userId is ""
    When I fetch posts for that user
    Then the response should contain no posts