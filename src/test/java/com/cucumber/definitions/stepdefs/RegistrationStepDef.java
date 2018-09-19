package com.cucumber.definitions.stepdefs;

import com.cucumber.definitions.pageobjects.RegistrationPage;
import cucumber.api.java.en.Given;

public class RegistrationStepDef extends BaseStepDef {

    private RegistrationPage registrationPage;

    @Given("^the user visits the training registration page$")
    public void theUserVisitsTheTrainingRegistrationPage() {
        registrationPage.navigateToPage("http://testautomationschool.nl/angular/");
    }
}
