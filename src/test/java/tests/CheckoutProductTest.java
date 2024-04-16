package tests;

import base.Browser;
import io.github.cdimascio.dotenv.Dotenv;
import model.Product;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutProductTest {
    Dotenv env = Dotenv.load();
    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CheckoutOverviewPage checkoutOverviewPage;
    CheckoutCompletePage checkoutCompletePage;
    Browser browser;

    @BeforeMethod
    public void setup() {
        // Get the browser name from the environment variable
        browser = new Browser();
        String browserName = env.get("browserName");
        // Initialize browser and open the application URL
        driver = browser.initializeBrowserAndOpenApplicationURL(browserName);
        // Initialize pageObjects
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void testCheckoutProduct() {
        String product1_description = "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.";
        Product product1 = new Product("Sauce Labs Backpack", product1_description, 29.99);
        String product2_description = "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.";
        Product product2 = new Product("Sauce Labs Bike Light",product2_description,9.99);
        Product[] products = {product1, product2};

        // Login using id: standard_user and password: secret_sauce
        loginPage.validateLoginPage();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        homePage.validateHomePageDisplayed();
        // "Add to cart" from home page "sauce labs backpack"
        homePage.clickProductAddToCartButton(product1.getName());
        homePage.headerSection.validateCartBadgeQty(1);
        product1.setQuantity(1);
        // Open product page for "sauce labs bike light"
        homePage.openProductDetails(product2.getName());
        productPage.validateProductPageDisplayed();
        productPage.validateProductDetails(product2);
        // Click on "Add to cart"
        productPage.clickAddToCartProduct();
        productPage.headerSection.validateCartBadgeQty(2);
        product2.setQuantity(1);
        // Click on shopping cart icon
        productPage.headerSection.clickShoppingCartIcon();
        cartPage.validateCartPagDisplayed();
        cartPage.validateProductDetails(products);
        // Click on "Checkout" button
        cartPage.clickCheckout();
        checkoutPage.validateCheckoutPageIsDisplayed();
        // Enter First Name: "dummy", Last Name: "", Zip Code: "28654"
        checkoutPage.enterCustomerInformation("dummy", "", "28654");
        checkoutPage.clickContinue();
        // Verify error message is displayed: "Error: Last Name is required"
        checkoutPage.verifyCheckoutError("Error: Last Name is required");
        // Enter Last Name: "user"
        checkoutPage.enterCustomerInformation("dummy", "user", "28654");
        // Click on Continue
        checkoutPage.clickContinue();
        checkoutPage.validateCheckoutNotError();
        checkoutOverviewPage.validateCheckoutOverviewPageIsDisplayed();
        checkoutOverviewPage.validatePaymentInfo();
        checkoutOverviewPage.validateShippingInfo("Free Pony Express Delivery!");
        double subtotal = product1.getPrice() + product2.getPrice();
        // Verify Price total = $43.18
        checkoutOverviewPage.validateTotalPrice(subtotal,8,43.18);
        // Verify products: "sauce labs backpack" and "sauce labs bike light" are displayed in checkout overview
        checkoutOverviewPage.validateProductOverview(products);
        // Click on Finish
        checkoutOverviewPage.clickFinish();
        checkoutCompletePage.validateCheckoutComplete();
        // Click on Back to Home
        checkoutCompletePage.clickBackHome();
        // Verify user is on Home screen
        homePage.validateHomePageDisplayed();
        // Click on hamburger menu and click on "Logout"
        homePage.headerSection.clickLogoutLink();
        // Verify user is on login screen
        loginPage.validateLoginPage();
    }
}
