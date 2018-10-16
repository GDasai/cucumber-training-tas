Feature: Registration
  AC 1: The user should be able to apply his registration

  Scenario: Fill in the registration form
    Given the user has opened a browser and visits the registration page
    And an user with firstname "Jantje"
    When the user completes the registration form
    And the user clicks on the overview button
    Then the firstname is visible on the overview screen
