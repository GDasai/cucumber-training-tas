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

    @Then("^the firstname \"([^\"]*)\" is visible on the overview screen$")
    public void theFirstnameIsVisibleOnTheOverviewScreen(String firstname) {
        assertEquals(firstname, overviewPage.getFirstnameOverview());
    }

    @Then("^the prefix \"([^\"]*)\" is visible on the overview screen$")
    public void thePrefixIsVisibleOnTheOverviewScreen(String prefix) {
        assertEquals(prefix, overviewPage.getPrefixOverview());
    }

    @Then("^the surname \"([^\"]*)\" is visible on the overview screen$")
    public void theSurnameIsVisibleOnTheOverviewScreen(String surname) {
        assertEquals(surname, overviewPage.getSurnameOverview());
    }

    @Then("^the date of birth \"([^\"]*)\" is visible on the overview screen$")
    public void theDateOfBirthIsVisibleOnTheOverviewScreen(String dateofbirth) {
        assertEquals(dateofbirth, overviewPage.getDateOfBirthOverview());
    }

    @Then("^the profession \"([^\"]*)\" is visible on the overview screen$")
    public void theProfessionIsVisibleOnTheOverviewScreen(String profession) {
        assertEquals(profession, overviewPage.getProfessionOverview());
    }

    @Then("^the gender \"([^\"]*)\" is visible on the overview screen$")
    public void theGenderIsVisibleOnTheOverviewScreen(String gender) {
        assertEquals(gender, overviewPage.getGenderOverview());
    }

    @Then("^the emailaddress \"([^\"]*)\" is visible on the overview screen$")
    public void theEmailaddressIsVisibleOnTheOverviewScreen(String email) {
        assertEquals(email, overviewPage.getEmailOverview());
    }

    @Then("^the remarks \"([^\"]*)\" is visible on the overview screen$")
    public void theRemarksIsVisibleOnTheOverviewScreen(String remarks) {
        assertEquals(remarks, overviewPage.getRemarkOverview());
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
