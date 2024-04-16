package pages;

import base.component.HeaderSection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    public HeaderSection headerSection;
    @FindBy(xpath = "//span[text()='Products']")
    private WebElement pageTitle;

    public HomePage(WebDriver driver) {
        super(driver);
        headerSection = new HeaderSection(driver);
        PageFactory.initElements(driver, this);
    }
    private WebElement getProductTitleElement(String productName) {
        return driver.findElement(By.xpath("//div[@data-test='inventory-item-name' and contains(text(),'"+ productName +"')]"));
    }

    private WebElement getProductDescriptionElement(String productName){
        return driver.findElement(By.xpath("//div[@data-test='inventory-item-name' and contains(text(),'"+ productName +"')]/ancestor::a/following-sibling::div[@class='inventory_item_desc']"));
    }

    private WebElement getProductPriceElement(String productName){
        return driver.findElement(By.xpath("//button[@id='add-to-cart-" + productName.replaceAll(" ", "-").toLowerCase() + "']/preceding-sibling::div[@class='inventory_item_price']"));
    }

    private WebElement getAddToCartButton(String productName) {
        return driver.findElement(By.xpath("//button[@id='add-to-cart-" + productName.replaceAll(" ", "-").toLowerCase() + "']"));
    }

    public void validateHomePageDisplayed(){
        assertElementDisplayed(pageTitle);
        headerSection.validateHeaderTitleDisplayed();
        headerSection.validateCartIconDisplayed();
        headerSection.validateHamburgerMenuDisplayed();
    }
    public void clickProductAddToCartButton(String productName){
        WebElement productAddToCartButton = getAddToCartButton(productName);
        click(productAddToCartButton);
    }

    public void openProductDetails(String productName){
        WebElement productTitle = getProductTitleElement(productName);
        click(productTitle);
    }

    public String getProductDescription(String productName) {
        WebElement productDescriptionElement = getProductDescriptionElement(productName);
        return productDescriptionElement.getText();
    }

    public String getProductPrice(String productName) {
        WebElement productPriceElement = getProductPriceElement(productName);
        return productPriceElement.getText();
    }
}