package com.cucumber.definitions.pageobjects;

import com.cucumber.driver.CukeConfigurator;
import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Slf4j
@SuppressWarnings("unused")
@Component
public class BasePage extends CukeConfigurator {

    public WebDriver webDriver;
    private static final long SECONDS_PAGELOAD_REFRESH = 5;

    public BasePage(final WebDriver webdriver) {
        this.webDriver = webdriver;
    }

    private WebDriverWait pauseQuickly() {
        return new WebDriverWait(webDriver, SECONDS_PAGELOAD_REFRESH);
    }

    public void sleep(final Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private <V> V expectShortly(final ExpectedCondition<V> webElementExpectedCondition) {
        return new WebDriverWait(webDriver, timeOutInterval).until(webElementExpectedCondition);
    }

    public void goBack() {
        webDriver.navigate().back();
    }

    public void get(String url) {
        webDriver.get(url);
    }

    public boolean onHomePage() {
        return getUrl().equals(format("https://%s", targetHostName));
    }

    public void openHomePage() {
        get(format("https://%s", targetHostName));
        assertValidPage();
    }

    public byte[] getScreenshot() {
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
    }

    public boolean canScreenshot() {
        return TakesScreenshot.class.isInstance(webDriver);
    }

    public String executeScript(final String string, final Object... args) {
        if (JavascriptExecutor.class.isInstance(webDriver)) {
            return (String) ((JavascriptExecutor) webDriver).executeScript(format(string, args));
        }
        throw new RuntimeException("Cannot execute JavaScript");
    }

    public void scrollDownTo(String locator) {
        WebElement element = findElement(By.id(locator));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript("window.scrollBy(0,500)", "", element);
    }

    /**
     * Officially the 'path' component of a url does not contain the first slash ("/"), but seeing that for testing purposes it's easier to
     * pass an absolute url with a "/" that's what's expected for the format parameter
     *
     * @param format url starting with "/"
     * @param args   additional arguments.
     */
    public void openPath(final String format, final Object... args) {
        get(format("https://%s%s", targetHostName, format(format, args)));
        assertValidPage();
    }

    public Cookie getCookie(String cookieName) {
        return webDriver.manage().getCookieNamed(cookieName);
    }

    public String getUrl() {
        return webDriver.getCurrentUrl();
    }

    public URL getURL() {
        try {
            return new URL(getUrl());
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void inputVisible(final WebElement element, final String value, final Keys... keys) {
        element.click();
        element.clear();
        element.sendKeys(value);
        Arrays.asList(keys).forEach(element::sendKeys);
    }

    public List<WebElement> elements(final By by) {
        return expectShortly(presenceOfAllElementsLocatedBy(by));
    }

    public WebElement element(final By by) {
        final List<WebElement> es = elements(by);
        return es.get(0);
    }

    public WebElement last(final By by) {
        final List<WebElement> es = elements(by);
        return es.get(es.size() - 1);
    }

    private WebElement elementIfVisible(WebElement element) {
        return element.isDisplayed() ? element : null;
    }

    public boolean presentAndVisible(final By by) {
        List<WebElement> elements = webDriver.findElements(by);
        return elements.size() >= 1 && elements.get(0).isDisplayed();
    }

    public boolean pollPresentAndVisible(final By by) {
        return pollVisible(by) != null;
    }

    public WebElement pollVisible(final By by) {
        return pollShortly().until(visibilityOfElementLocated(by));
    }

    public void pollClickFirstVisible(final By by) {
        pollShortly().until(presenceOfAllElementsLocatedBy(by)).stream().filter(WebElement::isDisplayed).findFirst().get().click();
    }

    private Wait<WebDriver> pollShortly() {
        return new FluentWait<>(webDriver).withTimeout(timeOutInterval, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    private Wait<WebDriver> pollVeryShortly() {
        return new FluentWait<>(webDriver).withTimeout(timeOutInterval, TimeUnit.SECONDS).pollingEvery(100, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
    }

    private <T> Wait<T> pollShortly(T t) {
        return new FluentWait<>(t).withTimeout(timeOutInterval, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }

    public void assertPath(final String path) {
        pollShortly().until(ExpectedConditions.urlContains(path));
    }

    public static ExpectedCondition<Boolean> urlMatches(final Matcher<String> matcher) {
        return new ExpectedCondition<Boolean>() {
            private String currentUrl = "";

            public Boolean apply(WebDriver driver) {
                this.currentUrl = driver.getCurrentUrl();
                return matcher.matches(currentUrl);
            }

            public String toString() {
                return String.format("Url should match with the matcher. Current url: \"%s\"", this.currentUrl);
            }
        };
    }

    /**
     * Polls shortly until either the element using the given by selector finds a visible element, or
     * the current page's url matches using the given matcher.
     *
     * @param by  element locator
     * @param url url matcher
     * @throws TimeoutException on {@link #pollShortly()} timeout
     */
    public void assertVisibleOrPath(final By by, final Matcher<String> url) {
        pollShortly().until(or(visibilityOfElementLocated(by), urlMatches(url)));
    }

    /**
     * Polls shortly until either the element using the given by selector finds a visible element, or
     * the current page's url matches using the given matcher.
     *
     * @param by   element locator
     * @param url1 url to possibly match
     * @param url2 url to possibly match
     * @throws TimeoutException on {@link #pollShortly()} timeout
     */
    public void assertVisibleOrContainsEitherPath(final By by, final String url1, final String url2) {
        pollShortly().until(or(visibilityOfElementLocated(by), urlContains(url1), urlContains(url2)));
    }

    /**
     * Asserts that the path matches either of the 2 provided paths
     *
     * @param url1 possible path 1
     * @param url2 possible path 2
     */

    public void assertEitherPaths(final String url1, final String url2) {
        pollShortly().until(or(urlContains(url1), urlContains(url2)));
    }

    public void assertVisible(final By by) {
        pollShortly().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement assertVisible(final WebElement webElement, final By by) {
        Wait<WebElement> wait = pollShortly(webElement);
        return wait.until(element -> elementIfVisible(webElement.findElement(by)));
    }

    public void assertNotVisible(final By by) {
        pollShortly().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void assertVisible(final By by, boolean expectedVisibility) {
        if (expectedVisibility) {
            assertVisible(by);
        } else {
            assertNotVisible(by);
        }
    }

    public void assertVisible(final WebElement webElement, final By by, boolean expectedVisibility) {
        if (expectedVisibility) {
            assertVisible(webElement, by);
        } else {
            assertNotVisible(webElement, by);
        }
    }

    public void assertNotVisible(final WebElement webElement, final By by) {
        Wait<WebElement> wait = pollShortly(webElement);
        wait.until(invisible -> isInvisible(webElement.findElement(by)));
    }

    private boolean isInvisible(final WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return true;
        }
    }

    public void assertPresent(final By by) {
        pollShortly().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void assertPresentDynamic(final By by) {
        pollVeryShortly().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void assertNotPresent(final By by) {
        pollShortly().until(ExpectedConditions.not(presenceOfAllElementsLocatedBy(by)));
    }

    public void assertText(final By by, String text) {
        pollShortly().until(textToBePresentInElementLocated(by, text));
    }

    public void assertTextInValue(final By by, String text) {
        pollShortly().until(ExpectedConditions.textToBePresentInElementValue(by, text));
    }

    public void assertValidPage() {
        if (!getUrl().endsWith("/404")) {
            Assert.assertEquals("Only Testing", webDriver.getTitle());
        }
    }

    public void focusOnTab(int tabIndexNo) {
        pollShortly().until(driver -> driver.getWindowHandles().size() > tabIndexNo);
        webDriver.switchTo().window(new ArrayList<>(webDriver.getWindowHandles()).get(tabIndexNo));
    }

    public void openTab() {
        webDriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
    }

    public void closeTab() {
        webDriver.close();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public void turnOnImplicitWaits(final int time, final TimeUnit timeUnit) {
        webDriver.manage().timeouts().implicitlyWait(time, timeUnit);
    }

    public void turnOffImplicitWaits() {
        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    private WebDriverWait getWebDriverWait(final long numberOfSeconds) {
        return new WebDriverWait(webDriver, numberOfSeconds);
    }

    private WebDriverWait getWebDriverWait() {
        return getWebDriverWait(timeOutInterval);
    }

    public WebElement findElement(final By locator) {
        return webDriver.findElement(locator);
    }

    public List<WebElement> findElements(final By locator) {
        return webDriver.findElements(locator);
    }

    public void waitForPageToLoad(final By waitForElement) {
        waitForPageToLoad(timeOutInterval, waitForElement);
    }

    public void waitForPageToLoad(final long numberOfSeconds, final By locator) {
        final WebDriverWait wait = new WebDriverWait(webDriver, numberOfSeconds);
        wait.withMessage("Element [" + locator.toString() + "] is not currently visible and so may not be interacted with");
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void refreshPage() {
        webDriver.navigate().refresh();
    }

    public void navigateBack() {
        webDriver.navigate().back();
    }

    public void quitWebDriver() {
        webDriver.quit();
    }

    public void closeWebDriver() {
        webDriver.close();
    }

    public void navigateToEnvironment(final String environment) throws AWTException {
        navigateToEnvironment(environment, "/");
    }

    public void navigateToEnvironment(final String environment, final String path) {
        navigateToPage("http://" + environment);
    }

    public void navigateToWebsite() throws AWTException {
        navigateToPage("https://cucumber.io/docs/reference/browser-automation");
    }

    public void navigateToPage(final String url) {
        webDriver.navigate().to(url);
    }

    public void navigateToPage(final URL url) {
        webDriver.navigate().to(url);
    }

    public Set<Cookie> getCookies() {
        return webDriver.manage().getCookies();
    }

    public void deleteAllCookies() {
        webDriver.manage().deleteAllCookies();
    }

    public void addCookie(final String name, final String value) {
        webDriver.manage().addCookie(new Cookie(name, value));
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public Object executeJavaScript(final String script) {
        return ((JavascriptExecutor) webDriver).executeScript(script);
    }

    public String getAttributeOfElement(final By locator, final String attr) {
        return findElement(locator).getAttribute(attr);
    }

    /* elementen  */
    public void waitForElementPresent(final By locator, final long numberOfSeconds) {
        final WebDriverWait wait = getWebDriverWait(numberOfSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementPresent(final By locator) {
        waitForElementPresent(locator, timeOutInterval);
    }

    public void waitForElementVisible(final By locator, final long numberOfSeconds) {
        final WebDriverWait wait = getWebDriverWait(numberOfSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementVisible(final By locator) {
        waitForElementVisible(locator, timeOutInterval);
    }

    public void sendKey(final By locator, final Keys key) {
        findElement(locator).sendKeys(key);
    }

    public void sendTab(final By locator) {
        sendKey(locator, Keys.TAB);
    }

    public boolean inputElementIsEnabled(final By locator) {
        return findElement(locator).isEnabled();
    }

    public void assertInputElementIsEnabled(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(isEnabledOfElementLocated(locator, true));
    }

    public void assertInputElementIsDisabled(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(isEnabledOfElementLocated(locator, false));
    }

    public void buttonClick(final By locator) {
        waitForElementVisible(locator);
        findElement(locator).click();
    }

    public void assertButtonVisibleAndEnabled(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(isEnabledOfElementLocated(locator, true));
    }

    public void assertButtonVisibleAndDisabled(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(isEnabledOfElementLocated(locator, false));
    }

    public void assertButtonVisible(final By locator) {
        waitForElementVisible(locator);
    }

    public void returnButtonIsClickable(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void checkBoxVisibleAndCheck(final By locator) {
        waitForElementVisible(locator);
        final WebElement element = findElement(locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void checkBoxVisibleAndUncheck(final By locator) {
        waitForElementVisible(locator);
        final WebElement element = findElement(locator);
        if (element.isSelected()) {
            element.click();
        }
    }

    public void assertElementVisibleAndCheck(final By locator) {
        waitForElementVisible(locator);
        Assert.assertEquals(findElement(locator).isSelected(), true);
    }

    public void assertElementVisibleAndUncheck(final By locator) {
        waitForElementVisible(locator);
        Assert.assertEquals(findElement(locator).isSelected(), true);
    }

    public boolean checkboxIsSelected(final By locator) {
        return findElement(locator).isSelected();
    }

    public void radioButtonVisibleAndSelect(final By locator) {
        waitForElementVisible(locator);
        findElement(locator).click();
    }

    public void radioButtonVisibleAndSelectByIndex(final By locator, final int option) {
        waitForElementVisible(locator);
        final List<WebElement> radios = findElements(locator);
        if (option > 0 && option <= radios.size()) {
            radios.get(option - 1).click();
        } else {
            throw new NotFoundException("option " + option + " not found (counting as 1,2,3,...)");
        }
    }

    public void assertRadioButtonVisibleAndSelected(final By locator) {
        waitForElementVisible(locator);
        Assert.assertEquals(findElement(locator).isSelected(), true);
    }

    public void assertRadioButtonVisibleAndNotSelected(final By locator) {
        waitForElementVisible(locator);
        Assert.assertEquals(findElement(locator).isSelected(), false);
    }

    public boolean radioButtonIsSelected(final By locator) {
        waitForElementVisible(locator);
        return findElement(locator).isSelected();
    }

    public String dropdownGetTextFirstSelected(final By locator) {
        waitForElementVisible(locator);
        WebElement selectedOption = new Select(findElement(locator)).getFirstSelectedOption();
        return selectedOption.getText();
    }

    public List<String> dropdownGetTextAllSelected(final By locator) {
        waitForElementVisible(locator);
        final List<WebElement> selectedOptions = new Select(findElement(locator)).getAllSelectedOptions();
        final List<String> selectedOptionsText = new ArrayList<String>();
        for (WebElement element : selectedOptions) {
            selectedOptionsText.add(element.getText());
        }
        return selectedOptionsText;
    }

    public void dropdownSelectByValue(final By locator, final String... listOfValues) {
        waitForElementVisible(locator);
        if (listOfValues != null && listOfValues.length > 0) {
            final Select select = new Select(findElement(locator));
            if (select.isMultiple()) {
                select.deselectAll();
                for (String value : listOfValues) {
                    select.selectByValue(value);
                }
            } else {
                select.selectByValue(listOfValues[0]);
            }
        } else {
            throw new IllegalArgumentException("at least one value is required to select");
        }
    }

    public void dropdownSelectByText(final By locator, final String... listOfTextValues) {
        waitForElementVisible(locator);
        if (listOfTextValues != null && listOfTextValues.length > 0) {
            final Select select = new Select(findElement(locator));
            if (select.isMultiple()) {
                select.deselectAll();
                for (String text : listOfTextValues) {
                    select.selectByVisibleText(text);
                }
            } else {
                select.selectByVisibleText(listOfTextValues[0]);
            }
        } else {
            throw new IllegalArgumentException("at least one text value is required to select");
        }
    }

    public void dropdownSelectByIndex(final By locator, final int... listOfIndexValues) {
        waitForElementVisible(locator);
        if (listOfIndexValues != null && listOfIndexValues.length > 0) {
            final Select select = new Select(findElement(locator));
            if (select.isMultiple()) {
                select.deselectAll();
                for (int index : listOfIndexValues) {
                    select.selectByIndex(index);
                }
            } else {
                select.selectByIndex(listOfIndexValues[0]);
            }
        } else {
            throw new IllegalArgumentException("at least one index value is required to select");
        }
    }

    public void assertDropdownSelectedContainsText(final By locator, final String value) {
        waitForElementVisible(locator);
        final Select select = new Select(findElement(locator));
        final List<WebElement> selectedOptions = select.getAllSelectedOptions();
        boolean hasFoundValue = false;
        for (WebElement option : selectedOptions) {
            if (option.getText().equals(value)) {
                hasFoundValue = true;
            }
        }
        Assert.assertTrue("Could not find " + value + " in the dropdown menu", hasFoundValue);
    }


    public void textInputSetText(final By locator, final String value) {
        waitForElementVisible(locator);
        final WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    public void textInputAppendText(final By locator, final String value) {
        waitForElementVisible(locator);
        findElement(locator).sendKeys(value);
    }

    public String textInputGetText(final By locator) {
        waitForElementVisible(locator);
        return findElement(locator).getText();
    }

    public String textInputGetValue(final By locator) {
        waitForElementVisible(locator);
        return getAttributeOfElement(locator, "value");
    }

    public boolean textInputIsEnabled(final By locator) {
        waitForElementVisible(locator);
        return findElement(locator).isEnabled();
    }

    public void assertTextInputIsEnabled(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(isEnabledOfElementLocated(locator, true));
    }

    public void assertTextInputIsDisabled(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(isEnabledOfElementLocated(locator, false));
    }

    public void assertTextInputContainsText(final By locator, final String text) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public void assertTextInputContainsAnyText(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(anyTextToBePresentInElementLocated(locator));
    }

    public void assertTextInputContainsValue(final By locator, final String text) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.textToBePresentInElementValue(locator, text));
    }

    public void assertTextInputContainsAnyValue(final By locator) {
        final WebDriverWait wait = getWebDriverWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(anyTextToBePresentInElementValue(locator));
    }

    private static ExpectedCondition<Boolean> isEnabledOfElementLocated(final By locator, final boolean isEnabled) {
        return new ExpectedCondition<Boolean>() {
            private boolean elementIsEnabled;

            public Boolean apply(final WebDriver driver) {
                elementIsEnabled = driver.findElement(locator).isEnabled();
                return isEnabled == elementIsEnabled;
            }

            public String toString() {
                return String.format("Expected element to be enabled \"%s\". Element enabled is: \"%s\"", isEnabled, elementIsEnabled);
            }
        };
    }

    private static ExpectedCondition<Boolean> anyTextToBePresentInElementLocated(final By locator) {
        return new ExpectedCondition<Boolean>() {
            private String text;

            public Boolean apply(final WebDriver driver) {
                text = driver.findElement(locator).getText();
                return !text.isEmpty();
            }

            public String toString() {
                return String.format("Element contains text \'%s\'", text);
            }
        };
    }

    public static ExpectedCondition<Boolean> anyTextToBePresentInElementValue(final By locator) {
        return new ExpectedCondition<Boolean>() {
            private String text;

            public Boolean apply(final WebDriver driver) {
                try {
                    text = driver.findElement(locator).getAttribute("value");
                    return text != null ? Boolean.valueOf(!text.isEmpty()) : Boolean.valueOf(false);
                } catch (StaleElementReferenceException var3) {
                    return null;
                }
            }

            public String toString() {
                return String.format("text (\'%s\') to be the value of element located by %s", text, locator);
            }
        };
    }

    private void shortFluentWait(final Function<WebDriver, Boolean> function) {
        new FluentWait<>(webDriver).withTimeout(timeOutInterval, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
                .until(function);
    }

    private String getCurrentPath() {
        try {
            return new URL(getCurrentUrl()).getPath();
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
