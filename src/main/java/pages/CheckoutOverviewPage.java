package pages;

import base.BasePage;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CheckoutOverviewPage extends BasePage {
    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(css = "[data-test='payment-info-label']")
    private WebElement paymentInfoLabel;

    @FindBy(css = "[data-test='payment-info-value']")
    private WebElement paymentInfoValue;

    @FindBy(css = "[data-test='shipping-info-label']")
    private WebElement shippingInfoLabel;

    @FindBy(css = "[data-test='shipping-info-value']")
    private WebElement shippingInfoValue;

    @FindBy(css = "[data-test='total-info-label']")
    private WebElement priceTotalLabel;

    @FindBy(css = "[data-test='subtotal-label']")
    private WebElement priceSubtotalValue;

    @FindBy(css = "[data-test='tax-label']")
    private WebElement priceTaxValue;

    @FindBy(css = "[data-test='total-label']")
    private WebElement priceTotalValue;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void validateCheckoutOverviewPageIsDisplayed() {
        waitUntilDisplayed(pageTitle);
        assertElementToHaveText(pageTitle,"Checkout: Overview");
    }

    public void validatePaymentInfo() {
        assertElementToHaveText(paymentInfoLabel,"Payment Information:");
        assertElementContainsText(paymentInfoValue, "SauceCard #");
    }

    public void validateShippingInfo(String expectedText) {
        assertElementToHaveText(shippingInfoLabel,"Shipping Information:");
        assertElementContainsText(shippingInfoValue, expectedText);
    }

    public String convertToString(double value) {
        return String.format("%.2f", value);
    }
    public void validateTotalPrice(double expectedSubTotal, double TaxRatio, double expectedTotal) {
        String expectedSubTotalText = convertToString(expectedSubTotal);
        String expectedTaxValue = convertToString(expectedSubTotal * TaxRatio / 100);
        String expectedTotalText = convertToString(expectedTotal);

        assertElementToHaveText(priceTotalLabel,"Price Total");
        assertElementToHaveText(priceSubtotalValue,"Item total: $"+ expectedSubTotalText);
        assertElementToHaveText(priceTaxValue,"Tax: $"+ expectedTaxValue);
        assertElementContainsText(priceTotalValue, "Total: $"+ expectedTotalText);
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

    public void validateProductOverview(Product[] products) {
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

    public void clickFinish(){
        click(finishButton);
    }

    public void clickCancel(){
        click(cancelButton);
    }
}
