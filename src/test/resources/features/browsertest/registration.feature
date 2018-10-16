Feature: Registration
  AC 1: The user should be able to apply his registration

  Scenario: Fill in the registration form
    Given the user has opened a browser and visits the registration page
    And an user with firstname "Jantje"
    And an user with prefix "de"
    And an user with surname "Tester"
    And an user with date of birth "24-11-1987"
    And an user with profession "Tester"
    And an user with "Male" as gender
    And an user with emailaddress "jantjedetester@test.nl"
    And an user with remarks "Dit zijn wat opmerkingen"
    When the user completes the registration form
    And the user clicks on the checkbox to save
    And the user answer the question with Yes
    And the user clicks on the button the activate an alertbox
    And the user confirms the alert box
    And the user clicks on the overview button

    Then the firstname is visible on the overview screen
    And the prefix is visible on the overview screen
    And the surname is visible on the overview screen
    And the date of birth is visible on the overview screen
    And the profession is visible on the overview screen
    And the gender is visible on the overview screen
    And the emailaddress is visible on the overview screen
    And the remarks is visible on the overview screen
    And the checkbox "true" is visible on the overview screen
    And the question answer "Yes" is visible on the overview screen