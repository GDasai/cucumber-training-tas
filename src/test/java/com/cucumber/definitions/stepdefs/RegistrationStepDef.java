package com.cucumber.definitions.stepdefs;

import com.cucumber.definitions.pageobjects.RegistrationPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;

import javax.annotation.PostConstruct;

public class RegistrationStepDef extends BaseStepDef {
    private RegistrationPage registrationPage;

    @PostConstruct
    public void setUpLogin() {
        registrationPage = PageFactory.initElements(webDriver, RegistrationPage.class);
    }

    @Given("^the user has opened a browser and visits the registration page$")
    public void theUserHasOpenedABrowserAndVisitsTheRegistrationPage() {
        registrationPage.navigateToPage("http://www.testautomationschool.nl/registrationdemo/");
    }

    @Given("^an user with firstname \"([^\"]*)\"$")
    public void anUserWithFirstname(String firstName) {
        registrationObject.setFirstName(firstName);
    }

    @When("^the user completes the registration form$")
    public void theUserCompletesTheRegistrationForm() {
        registrationPage.enterFirstname(registrationObject.getFirstName());
    }

    @And("^the user clicks on the overview button$")
    public void theUserClicksOnTheOverviewButton() {
        registrationPage.clickOverviewButton();
    }
}
