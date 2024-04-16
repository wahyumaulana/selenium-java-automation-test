package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorText;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void validateCheckoutPageIsDisplayed() {
        assertElementToHaveText(pageTitle,"Checkout: Your Information");
    }

    public void clickContinue(){
        click(continueButton);
    }

    public void clickCancel(){
        click(cancelButton);
    }

    public void enterCustomerInformation(String fistName, String lastName, String postalCode){
        input(firstNameField,fistName);
        input(lastNameField,lastName);
        input(postalCodeField,postalCode);
    }

    public void verifyCheckoutError(String errorMessage){
        assertElementToHaveText(errorText,errorMessage);
    }

    public void validateCheckoutNotError(){
        assertElementNotDisplayed(errorText);
    }
}