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
}
