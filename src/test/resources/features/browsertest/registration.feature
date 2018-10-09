Feature: Registration

  Scenario: Fill in the registration form
    Given the user has opened a browser and visits the registration page
    When the user enters his firstname with "Jantje"
    And the user enters his prefix with "de"
    And the user enters his surname with "Tester"
    And the user enters his date of birth with "24-11-1987"
    And the user enters his profession with "Tester"
    And the user chooses "Male" as gender from the dropdownlist
    And the user enters his emailaddress with "jantjedetester@test.nl"
    And the user enters his remarks with "Dit zijn wat opmerkingen"
    And the user clicks on the checkbox to save
    And the user answer the question with Yes
    And the user clicks on the button the activate an alertbox
    And the user confirms the alert box
    And the user clicks on the overview button

    Then the firstname "Jantje" is visible on the overview screen
    Then the prefix "de" is visible on the overview screen
    Then the surname "Tester" is visible on the overview screen
    And the date of birth "24-11-1987" is visible on the overview screen
    And the profession "Tester" is visible on the overview screen
    And the gender "Male" is visible on the overview screen
    And the emailaddress "jantjedetester@test.nl" is visible on the overview screen
    And the remarks "Dit zijn wat opmerkingen" is visible on the overview screen
    And the checkbox "true" is visible on the overview screen
    And the question answer "Yes" is visible on the overview screen