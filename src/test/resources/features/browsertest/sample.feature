@Smoketest

Feature: User wants to visit the demo login page
  AC 1: The user should be able to start a browser
  AC 2: The user should be able to navigate to the demo login page

  Scenario: 1 User logs in with valid credentials
    Given the user visits the training login page
    When the user enters the username with "cursus"
    And the user enters the password "selenium"
    And the user clicks the signin button
    Then the user should be on the default landing page of the cucumber website