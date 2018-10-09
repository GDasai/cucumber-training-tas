package com.cucumber.definitions.pageobjects;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertEquals;

public class OverviewPage extends BasePage {

    public OverviewPage(WebDriver webDriver) {
        super(webDriver);
        WebDriverRunner.setWebDriver(webDriver);
    }

    public String getFirstnameOverview() {
        return textInputGetValue(By.id("firstnameOverview"));
    }

    public String getPrefixOverview() {
        return textInputGetValue(By.id("prefixOverview"));
    }

    public String getSurnameOverview() {
        return textInputGetValue(By.id("surnameOverview"));
    }

    public String getDateOfBirthOverview() {
        return textInputGetValue(By.id("dateofbirthOverview"));
    }

    public String getProfessionOverview() {
        return textInputGetValue(By.id("profesionOverview"));
    }

    public String getGenderOverview() {
        return textInputGetValue(By.id("genderOverview"));
    }

    public String getEmailOverview() {
        return textInputGetValue(By.id("emailOverview"));
    }

    public String getRemarkOverview() {
        return textInputGetValue(By.id("remarkOverview"));
    }

    public String getCheckboxCheckedOverview() {
        return textInputGetValue(By.id("checkboxOverview"));
    }

    public String getEnjoyOverview() {
        return textInputGetValue(By.id("enjoyOverview"));
    }
}
