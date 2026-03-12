@regression
Feature: Validate emails in the comments are in the proper format
  @email_validation
  Scenario Outline: Validate emails in comments for posts created by user
    Given a username is "<username>"
    When I fetch posts for that user
    And I fetch comments for each post
    Then all emails in comments should be valid
    Examples:
      | username   |
      | Delphine   |
      | Bret       |
      | Antonette  |
      | Samantha   |
      | Pankaj     |