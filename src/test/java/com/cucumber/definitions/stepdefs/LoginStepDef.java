package com.cucumber.definitions.stepdefs;

import com.cucumber.definitions.pageobjects.LoginPage;
import com.cucumber.driver.CukeConfigurator;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Dan;
import cucumber.api.java.nl.En;
import cucumber.api.java.nl.Gegeven;
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

    @Gegeven("^de gebruiker heeft een browser geopend en bezoekt de login pagina$")
    @Given("^the user has opened a browser and visits the training login page$")
    public void openBrowserVisitTrainingLoginPage() throws AWTException {
        loginPage.navigateToEnvironment(cukeconfig.targetHostName);
    }

    @Then("^the user is on the login page$")
    public void verifyOnLoginPage() {
        loginPage.verifyPageTitle("Selenium demo pagina");
    }

    @En("^de gebruiker klikt op de aanmelden knop")
    @And("^the user clicks the signin button$")
    public void signIn() {
        loginPage.clickSignInButton();
    }

    @Dan("^komt de gebruiker op de homepagina van de cucumber website$")
    @Then("^the user should be on the default landing page of the cucumber website$")
    public void verifyOnCucumberPage() {
        loginPage.verifyPageTitle("BDD Testing & Collaboration Tools for Teams | Cucumber");
    }

    @Then("^the user should receive the following error message \"([^\"]*)\"$")
    public void verifyErrorMessage(String message) {
        loginPage.verifyText(By.id("loginerror"), message);
    }

    @When("^the user enters the username with \"([^\"]*)\"$")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("^the user enters the password \"([^\"]*)\"$")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Then("^the user is verifying text \"([^\"]*)\" on the page with id \"([^\"]*)\"$")
    public void theUserIsVerifyingTextOnThePageWithId(String pagetext, String id) {
        loginPage.verifyText(By.id(id), pagetext);
    }

    @And("^the user clicks the verklaring button$")
    public void theUserClicksTheVerklaringButton() {
        loginPage.clickVerklaringButton();
    }

    @And("^the user clicks the second radiobutton$")
    public void theUserClicksTheSecondRadiobutton() {
        loginPage.clickSecondRadiobutton();
    }

    @And("^the user checks the checkbox$")
    public void theUserChecksTheCheckbox() {
        loginPage.checkCheckbox();
    }

    @Als("^de gebruiker inlogt met valide inlog gegevens$")
    public void loginWithValidCredentials() {
        loginPage.enterUsername("cursus");
        loginPage.enterPassword("selenium");
    }
}

