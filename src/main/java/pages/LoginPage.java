package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//div[contains(text(),'Swag Labs')]")
    private WebElement pageTitle;

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement lockedOutErrorMessage;

    @FindBy(id = "login_credentials")
    private WebElement loginCredentialsSection;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void validateLoginPage() {
        assertElementDisplayed(pageTitle);
        assertElementDisplayed(loginCredentialsSection);
    }

    public void enterUsername(String username) {
        input(usernameField, username);
    }

    public void enterPassword(String password) {
        input(passwordField, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void validateLockedOutErrorMessage(String errorMessage) {
        assertElementToHaveText(lockedOutErrorMessage, "Epic sadface: " + errorMessage);
    }
}
