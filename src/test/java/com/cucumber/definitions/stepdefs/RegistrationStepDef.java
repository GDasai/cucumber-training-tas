package com.cucumber.definitions.stepdefs;

import com.cucumber.definitions.pageobjects.RegistrationPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;

import javax.annotation.PostConstruct;

public class RegistrationStepDef extends BaseStepDef {
    private RegistrationPage registrationPage;

    @PostConstruct
    public void setUpLogin() {
        registrationPage = PageFactory.initElements(webDriver, RegistrationPage.class);
    }

    @Given("^the user has opened a browser and visits the registration page$")
    public void theUserHasOpenedABrowserAndVisitsTheRegistrationPage() {
        registrationPage.navigateToPage("http://www.testautomationschool.nl/registrationdemo/");
    }

    @Given("^an user with firstname \"([^\"]*)\"$")
    public void anUserWithFirstname(String firstName) {
        registrationObject.setFirstName(firstName);
    }

    @Given("^an user with prefix \"([^\"]*)\"$")
    public void anUserWithPrefix(String prefix) {
        registrationObject.setPrefix(prefix);
    }

    @Given("^an user with surname \"([^\"]*)\"$")
    public void anUserWithSurname(String surname) {
        registrationObject.setSurname(surname);
    }

    @Given("^an user with date of birth \"([^\"]*)\"$")
    public void anUserWithDateOfBirth(String date) {
        registrationObject.setDateOfBirth(date);
    }

    @Given("^an user with profession \"([^\"]*)\"$")
    public void anUserWithProfession(String profession) {
        registrationObject.setProfession(profession);
    }

    @Given("^an user with \"([^\"]*)\" as gender$")
    public void anUserWithAsGender(String gender) {
        registrationObject.setGender(gender);
    }

    @Given("^an user with emailaddress \"([^\"]*)\"$")
    public void anUserWithEmailaddress(String email) {
        registrationObject.setEmail(email);
    }

    @Given("^an user with remarks \"([^\"]*)\"$")
    public void anUserWithRemarks(String remarks) {
        registrationObject.setRemarks(remarks);
    }

    @When("^the user completes the registration form$")
    public void theUserCompletesTheRegistrationForm() {
        registrationPage.enterFirstname(registrationObject.getFirstName());
        registrationPage.enterPrefix(registrationObject.getPrefix());
        registrationPage.enterSurname(registrationObject.getSurname());
        registrationPage.enterDateOfBirth(registrationObject.getDateOfBirth());
        registrationPage.enterProfession(registrationObject.getProfession());
        registrationPage.chooseGender(registrationObject.getGender());
        registrationPage.enterEmail(registrationObject.getEmail());
        registrationPage.enterRemarks(registrationObject.getRemarks());
    }

    @When("^the user enters his firstname with \"([^\"]*)\"$")
    public void theUserEntersHisFirstnameWith(String firstName) {
        registrationPage.enterFirstname(firstName);
    }

    @And("^the user enters his prefix with \"([^\"]*)\"$")
    public void theUserEntersHisPrefixWith(String prefix) {
        registrationPage.enterPrefix(prefix);
    }

    @And("^the user enters his surname with \"([^\"]*)\"$")
    public void theUserEntersHisSurnameWith(String surname) {
        registrationPage.enterSurname(surname);
    }

    @And("^the user enters his date of birth with \"([^\"]*)\"$")
    public void theUserEntersHisDateOfBirthWith(String dateofbirth) {
        registrationPage.enterDateOfBirth(dateofbirth);
    }

    @And("^the user enters his profession with \"([^\"]*)\"$")
    public void theUserEntersHisProfessionWith(String profession) {
        registrationPage.enterProfession(profession);
    }

    @And("^the user chooses \"([^\"]*)\" as gender from the dropdownlist$")
    public void theUserChoosesAsGenderFromTheDropdownlist(String gender) {
        registrationPage.chooseGender(gender);
    }

    @And("^the user enters his emailaddress with \"([^\"]*)\"$")
    public void theUserEntersHisEmailaddressWith(String email) {
        registrationPage.enterEmail(email);
    }

    @And("^the user enters his remarks with \"([^\"]*)\"$")
    public void theUserEntersHisRemarksWith(String remarks) {
        registrationPage.enterRemarks(remarks);
    }

    @And("^the user clicks on the checkbox to save$")
    public void theUserClicksOnTheCheckboxToSave() {
        registrationPage.checkDefaultCheckbox();
    }

    @And("^the user answer the question with Yes$")
    public void theUserAnswerTheQuestionYes() {
        registrationPage.chooseAnswerYes();
    }

    @And("^the user clicks on the button the activate an alertbox$")
    public void theUserClicksOnTheButtonTheActivateAnAlertbox() {
        registrationPage.clickButtonAlertBox();
    }

    @And("^the user clicks on the overview button$")
    public void theUserClicksOnTheOverviewButton() {
        registrationPage.clickOverviewButton();
    }

    @And("^the user confirms the alert box$")
    public void theUserConfirmsTheAlertBox() {
        registrationPage.clickAlertOk();
    }
}
