package com.cucumber.definitions.stepdefs;

import com.cucumber.definitions.pageobjects.LoginPage;
import com.cucumber.driver.CukeConfigurator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import javax.annotation.PostConstruct;
import java.awt.*;

public class LoginStepDef extends BaseStepDef {

    private LoginPage loginPage;
    private CukeConfigurator cukeconfig = new CukeConfigurator();

    @PostConstruct
    public void setUpLogin() {
        loginPage = PageFactory.initElements(webDriver, LoginPage.class);
    }

    @Given("^the user has opened a browser and visits the training login page$")
    public void openBrowserVisitTrainingLoginPage() throws AWTException {
        loginPage.navigateToEnvironment(cukeconfig.targetHostName);
    }

    @Then("^the user is on the login page$")
    public void verifyOnLoginPage() {
        loginPage.verifyPageTitle("Selenium demo pagina");
    }

    @Then("^the user should receive the following error message \"([^\"]*)\"$")
    public void verifyErrorMessage(String message) {
        loginPage.verifyText(By.id("loginerror"), message);
    }

    @When("^the user enters the username with \"([^\"]*)\"$")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @Then("^the user is verifying text \"([^\"]*)\" on the page with id \"([^\"]*)\"$")
    public void theUserIsVerifyingTextOnThePageWithId(String pagetext, String id) {
        loginPage.verifyText(By.id(id), pagetext);
    }
}

