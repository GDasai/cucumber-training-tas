@Smoketest

Feature: User wants to visit the website and login (sample.feature)
  AC 1: The user should be able to enter an username
  AC 2: The user should be able to enter a password
  AC 3: There should be an error message shown if the user tries to login with incorrect credentials
  AC 4: The user should be on the Cucumber page if the correct credentials are entered

  Scenario: 1 User logs in with valid credentials
    Given the user has opened a browser and visits the training login page
    When the user enters the username with "cursus"
    And the user enters the password "selenium"
    And the user clicks the signin button
    Then the user should be on the default landing page of the cucumber website