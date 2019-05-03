@RestTest

Feature: Get (personal) data via rest api calls

  Scenario: User calls web service to get a person by his/her name
    Given a person with the name John
    When a the user is retrieved data in JSON format
    Then the status code is 200
    And verify that element name has value "John"
    And verify that element age has value 30
    And verify that person has 3 cars
    And verify that the first cars is blue
