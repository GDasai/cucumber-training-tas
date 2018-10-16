package com.cucumber.definitions.pageobjects;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OverviewPage extends BasePage {

    public OverviewPage(WebDriver webDriver) {
        super(webDriver);
        WebDriverRunner.setWebDriver(webDriver);
    }

    public String getFirstnameOverview() {
        return textInputGetValue(By.id("firstnameOverview"));
    }
}
