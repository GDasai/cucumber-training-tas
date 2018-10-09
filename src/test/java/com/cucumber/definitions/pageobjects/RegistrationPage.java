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

    public void enterPrefix(String prefix) {
        textInputSetText(By.id("prefix"), prefix);
    }

    public void enterSurname(String surname) {
        textInputSetText(By.id("surname"), surname);
    }

    public void enterDateOfBirth(String dateofbirth) {
        textInputSetText(By.id("dateofbirth"), dateofbirth);
    }

    public void enterProfession(String profession) {
        textInputSetText(By.id("profession"), profession);
    }

    public void chooseGender(String gender) {
        dropdownSelectByText(By.id("gender"), gender);
    }

    public void enterEmail(String email) {
        textInputSetText(By.id("email"), email);
    }

    public void enterRemarks(String remarks) {
        textInputSetText(By.id("textarea1"), remarks);
    }

    public void checkDefaultCheckbox() {
        checkBoxVisibleAndCheck(By.id("defaultCheck1"));
    }

    public void chooseAnswerYes() {
        radioButtonVisibleAndSelect(By.id("gridRadios1"));
    }

    public void clickButtonAlertBox() {
        buttonClick(By.xpath("/html/body/app-root/app-heroes/div[2]/div[11]/button"));

    }

    public void clickOverviewButton() {
        buttonClick(By.id("GoToOverview"));
    }

    public void clickAlertOk() {
        confirmAlert();
    }
}
