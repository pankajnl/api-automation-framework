@comments_validation
Feature: Validate response details for comments api endpoint

  @positive_flow
  Scenario: Retrieve comments when valid postId is provided
    Given a postId is 81
    When I fetch comments for that post
    Then the response should contain the fields
      | postId |
      | id |
      | name |
      | email |
      | body |

  @positive_flow
  Scenario: Comments should belongs to the post when valid postId is provided
    Given a postId is 81
    When I fetch comments for that post
    Then all comments in the response should have postId as 81

  @positive_flow
  Scenario: Comments should contain valid comment ids when valid postId is provided
    Given a postId is 81
    When I fetch comments for that post
    Then each comments in the response should contain a valid id

  @positive_flow
  Scenario: No comments should be returned when post has no comments
    Given a postId is 100
    When I fetch comments for that post
    Then the response should contain no comments

  @positive_flow
  Scenario: Emails in comments should be in valid format when valid postId is provided
    Given a postId is 81
    When I fetch comments for that post
    Then all emails in comments should be valid

  #Considering the fact that email field is mandatory in the response
  @positive_flow
  Scenario: Comment email should not be empty when valid postId is provided
    Given a postId is 81
    When I fetch comments for that post
    Then all emails in comments should not be empty

  @negative_flow
  Scenario: No comments should be returned when invalid postId is provided
    Given a postId is 10000
    When I fetch comments for that post
    Then the response should contain no comments

  @negative_flow
  Scenario: No comments should be returned when postId is non numeric
    Given a postId is "test"
    When I fetch comments for that post
    Then the response should contain no comments

  @negative_flow
    Scenario: No comments should be returned when postId is empty
        Given a postId is ""
        When I fetch comments for that post
        Then the response should contain no comments