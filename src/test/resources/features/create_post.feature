@create_post @regression
Feature: Create Post API validation

  Scenario: Create a new post using external test data
    Given create post request data is loaded from "create_post_data.json"
    When I submit the create post API request
    Then new post should be created successfully