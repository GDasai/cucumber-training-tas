package com.cucumber.definitions.pageobjects;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage{

    public RegistrationPage(WebDriver webdriver) {
        super(webdriver);
        WebDriverRunner.setWebDriver(webDriver);
    }
}
