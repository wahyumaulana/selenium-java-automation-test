package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);

    }

    protected void waitUntilDisplayed(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void click(WebElement element) {
        waitUntilDisplayed(element);
        element.click();
    }

    protected void input(WebElement element, String text) {
        waitUntilDisplayed(element);
        element.clear();
        element.sendKeys(text);
    }

    protected void assertElementDisplayed(WebElement element) {
        waitUntilDisplayed(element);
        Assert.assertTrue(element.isDisplayed(), "Element is not displayed");
    }

    protected void assertElementNotDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            // Element is still displayed after the timeout
            throw new AssertionError("Element is still displayed: " + element.toString());
        }
    }


    protected void assertElementToHaveText(WebElement element, String expectedText) {
        waitUntilDisplayed(element);
        String actualText = element.getText().trim();
        Assert.assertEquals(actualText, expectedText, "Expected text is '" + expectedText + "', but actual text is " + actualText);
    }

    protected void assertElementContainsText(WebElement element, String expectedText){
        waitUntilDisplayed(element);
        String actualText = element.getText().trim();
        Assert.assertTrue(actualText.contains(expectedText), "Expected substring '" + expectedText + "' not found in actual string: " + actualText);
    }
}