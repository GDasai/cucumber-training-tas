package com.cucumber.definitions.stepdefs;

import com.cucumber.definitions.pageobjects.OverviewPage;
import cucumber.api.java.en.Then;
import org.openqa.selenium.support.PageFactory;

import javax.annotation.PostConstruct;

import static org.junit.Assert.assertEquals;

public class OverviewStepDef extends BaseStepDef {

    private OverviewPage overviewPage;

    @PostConstruct
    public void setUpLogin() {
        overviewPage = PageFactory.initElements(webDriver, OverviewPage.class);
    }

    @Then("^the firstname is visible on the overview screen$")
    public void theFirstnameIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getFirstName(), overviewPage.getFirstnameOverview());
    }

    @Then("^the prefix is visible on the overview screen$")
    public void thePrefixIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getPrefix(), overviewPage.getPrefixOverview());
    }

    @Then("^the surname is visible on the overview screen$")
    public void theSurnameIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getSurname(), overviewPage.getSurnameOverview());
    }

    @Then("^the date of birth is visible on the overview screen$")
    public void theDateOfBirthIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getDateOfBirth(), overviewPage.getDateOfBirthOverview());
    }

    @Then("^the profession is visible on the overview screen$")
    public void theProfessionIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getProfession(), overviewPage.getProfessionOverview());
    }

    @Then("^the gender is visible on the overview screen$")
    public void theGenderIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getGender(), overviewPage.getGenderOverview());
    }

    @Then("^the emailaddress is visible on the overview screen$")
    public void theEmailaddressIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getEmail(), overviewPage.getEmailOverview());
    }

    @Then("^the remarks is visible on the overview screen$")
    public void theRemarksIsVisibleOnTheOverviewScreen() {
        assertEquals(registrationObject.getRemarks(), overviewPage.getRemarkOverview());
    }

    @Then("^the checkbox \"([^\"]*)\" is visible on the overview screen$")
    public void theCheckboxIsVisibleOnTheOverviewScreen(String checkboxChecked) {
        assertEquals(checkboxChecked, overviewPage.getCheckboxCheckedOverview());
    }

    @Then("^the question answer \"([^\"]*)\" is visible on the overview screen$")
    public void theQuestionAnswerIsVisibleOnTheOverviewScreen(String answer) {
        assertEquals(answer, overviewPage.getEnjoyOverview());
    }
}
