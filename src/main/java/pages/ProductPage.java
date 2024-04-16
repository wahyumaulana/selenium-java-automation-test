package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.component.HeaderSection;
import base.BasePage;
import model.Product;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ProductPage extends BasePage {

    public HeaderSection headerSection;

    @FindBy(css = "[data-test='inventory-item-name']")
    private WebElement productTitleText;

    @FindBy(css = "[data-test='inventory-item-desc']")
    private WebElement productDescriptionText;

    @FindBy(xpath = "//img[@class='inventory_details_img']")
    private WebElement productImage;

    @FindBy(css = "[data-test='inventory-item-price']")
    private WebElement productPriceText;

    @FindBy(id = "add-to-cart")
    private WebElement addToCartButton;

    @FindBy(id= "remove")
    private WebElement removeButton;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.headerSection = new HeaderSection(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickAddToCartProduct() {
        click(addToCartButton);
    }

    public void clickRemoveProduct() {
        click(removeButton);
    }

    public void validateProductPageDisplayed(){
        assertElementToHaveText(backToProductsButton,"Back to products");
    }

    public void validateProductDetails(Product product) {
        String priceText = productPriceText.getText().replaceAll("[^0-9.]", "");
        double actualProductPrice = Double.parseDouble(priceText);
        double expectedProductPrice = product.getPrice();

        assertElementDisplayed(productImage);
        assertElementToHaveText(productTitleText, product.getName());
        assertElementToHaveText(productDescriptionText, product.getDescription());
        Assert.assertEquals(actualProductPrice,expectedProductPrice,"Product Price not Equals");
        assertElementDisplayed(addToCartButton);
    }
}
