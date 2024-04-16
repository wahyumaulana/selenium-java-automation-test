package pages;

import base.BasePage;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.lang.reflect.Array;

public class CartPage extends BasePage {

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void validateCartPagDisplayed() {
        assertElementToHaveText(pageTitle,"Your Cart");
    }

    public String getProductTitle(String productName) {
        String productTitleXPath = "//div[@data-test='inventory-item-name' and contains(text(),'" + productName + "')]";
        WebElement productQtyElement = driver.findElement(By.xpath(productTitleXPath));
        return productQtyElement.getText();
    }
    public Integer getProductQuantity(String productName) {
        String productQtyXPath = "//div[@data-test='inventory-item-name' and contains(text(),'" + productName + "')]/ancestor::div[@class='cart_item']//div[@class='cart_quantity']";
        WebElement productQtyElement = driver.findElement(By.xpath(productQtyXPath));
        String priceText = productQtyElement.getText().replaceAll("[^0-9.]", "");
        return Integer.parseInt(priceText);
    }

    public String getProductDescription(String productName) {
        String productDescriptionXPath = "//div[@data-test='inventory-item-name' and contains(text(),'" + productName + "')]/ancestor::div[@class='cart_item']//div[@class='inventory_item_desc']";
        WebElement productDescriptionElement = driver.findElement(By.xpath(productDescriptionXPath));
        return productDescriptionElement.getText();
    }

    public double getProductPrice(String productName) {
        String productPriceXPath = "//div[@data-test='inventory-item-name' and contains(text(),'" + productName + "')]/ancestor::div[@class='cart_item']//div[@class='inventory_item_price']";
        WebElement productPriceElement = driver.findElement(By.xpath(productPriceXPath));
        String priceText = productPriceElement.getText().replaceAll("[^0-9.]", "");
        return Double.parseDouble(priceText);
    }

    public void clickProductRemoveButton(String productName) {
        String productRemoveButtonXPath = "//div[@data-test='inventory-item-name' and contains(text(),'" + productName + "')]/ancestor::div[@class='cart_item']//button[contains(@data-test,'remove-')]";
        WebElement productRemoveButton = driver.findElement(By.xpath(productRemoveButtonXPath));
        productRemoveButton.click();
    }

    public void validateProductDetails(Product[] products) {
        for (Product product : products) {
            String productTitle = getProductTitle(product.getName());
            String productDescription = getProductDescription(product.getName());
            double productPrice = getProductPrice(product.getName());
            Integer productQty = getProductQuantity(product.getName());

            Assert.assertEquals(productTitle, product.getName(), "Product Name not Equals");
            Assert.assertEquals(productDescription, product.getDescription(), "Product Description not Equals");
            Assert.assertEquals(productPrice, product.getPrice(), "Product Price not Equals");
            Assert.assertEquals(productQty, product.getQuantity(), "Product Qty not Equals");
        }
    }

    public void clickContinueShopping(){
        click(continueShoppingButton);
    }

    public void clickCheckout(){
        click(checkoutButton);
    }
}