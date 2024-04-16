package tests;

import base.Browser;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest {
    Dotenv env = Dotenv.load();
    WebDriver driver;
    LoginPage loginPage;
    Browser browser = new Browser();

    @BeforeMethod
    public void setup() {

        // Get the browser name from the environment variable
        String browserName = env.get("browserName");

        // Initialize browser and open the application URL
        driver = browser.initializeBrowserAndOpenApplicationURL(browserName);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void LoginWithLockedOutUser() {
        // Perform login with locked_out_user credentials
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        // Verify error message
        loginPage.validateLockedOutErrorMessage("Sorry, this user has been locked out.");
    }
}
