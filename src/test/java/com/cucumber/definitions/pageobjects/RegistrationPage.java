package com.cucumber.definitions.pageobjects;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver webDriver) {
        super(webDriver);
        WebDriverRunner.setWebDriver(webDriver);
    }

    public void enterFirstname(String firstName) {
        textInputSetText(By.id("firstname"), firstName);
    }

    public void clickOverviewButton() {
        buttonClick(By.id("GoToOverview"));
    }
}
