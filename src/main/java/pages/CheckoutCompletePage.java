package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutCompletePage extends BasePage {

    @FindBy(css = ".pony_express")
    private WebElement completeImage;

    @FindBy(css = ".complete-header")
    private WebElement thankYouText;

    @FindBy(css = ".complete-text")
    private WebElement orderDescriptionText;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void validateCheckoutComplete() {
        assertElementDisplayed(completeImage);
        assertElementToHaveText(thankYouText,"Thank you for your order!");
        assertElementToHaveText(orderDescriptionText, "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        assertElementDisplayed(backHomeButton);
    }

    public void clickBackHome() {
        click(backHomeButton);
    }
}
