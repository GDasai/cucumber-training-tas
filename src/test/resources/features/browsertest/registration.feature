Feature: Registration
  AC 1: The user should be able to apply his registration

  Scenario: Fill in the registration form
    Given the user has opened a browser and visits the registration page
    Given an user with firstname "Jantje"
    Given an user with prefix "de"
    Given an user with surname "Tester"
    Given an user with date of birth "24-11-1987"
    Given an user with profession "Tester"
    Given an user with "Male" as gender
    Given an user with emailaddress "jantjedetester@test.nl"
    Given an user with remarks "Dit zijn wat opmerkingen"
    When the user completes the registration form
    And the user clicks on the checkbox to save
    And the user answer the question with Yes
    And the user clicks on the button the activate an alertbox
    And the user confirms the alert box
    And the user clicks on the overview button

    Then the firstname is visible on the overview screen
    Then the prefix is visible on the overview screen
    Then the surname is visible on the overview screen
    Then the date of birth is visible on the overview screen
    Then the profession is visible on the overview screen
    Then the gender is visible on the overview screen
    Then the emailaddress is visible on the overview screen
    Then the remarks is visible on the overview screen
    Then the checkbox "true" is visible on the overview screen
    Then the question answer "Yes" is visible on the overview screen