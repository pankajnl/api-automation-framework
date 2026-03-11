@user_validation
Feature: Validate response details for users api endpoint

  @positive_flow
  Scenario: Retrieve user details when valid username is provided
    Given a username is "Delphine"
    When I fetch details for that user
    Then the response should contain the fields
      | id |
      | name |
      | username |
      | email |
      | address |
      | phone |
      | website |
      | company |

  @positive_flow
  Scenario: User details retrieval should be case insensitive
    Given a username is "delphine"
    When I fetch details for that user
    Then the response should contain the fields
      | id |
      | name |
      | username |
      | email |
      | address |
      | phone |
      | website |
      | company |

  @positive_flow
  Scenario: Only one user should be returned when valid username is provided
    Given a username is "Delphine"
    When I fetch details for that user
    Then the response should contain only one user

  @positive_flow
  Scenario: User email should be in valid format when valid username is provided
    Given a username is "Delphine"
    When I fetch details for that user
    Then the email field in the response should be in valid email format

  @positive_flow
  Scenario: User details should contain valid id when valid username is provided
    Given a username is "Delphine"
    When I fetch details for that user
    Then the id field in the response should be a valid numeric value

  @negative_flow
  Scenario: User does not exist when invalid username is provided
    Given a username is "InvalidUser"
    When I fetch details for that user
    Then the response should indicate user not found

  @negative_flow
  Scenario: User does not exist when empty username is provided
    Given a username is ""
    When I fetch details for that user
    Then the response should indicate user not found

  @negative_flow
  Scenario: User does not exist when only special characters are provided as username
    Given a username is "@#$%"
    When I fetch details for that user
    Then the response should indicate user not found