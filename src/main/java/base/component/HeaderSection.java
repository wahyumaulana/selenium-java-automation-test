package base.component;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HeaderSection extends BasePage {
    @FindBy(xpath = "//div[contains(text(),'Swag Labs')]")
    private WebElement headerTitle;
    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement cartIcon;
    @FindBy(css = "[data-test='shopping-cart-badge']")
    private WebElement cartBadgeQty;
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public HeaderSection(WebDriver driver) {
        super(driver);
    }

    public void validateHeaderTitleDisplayed() {
        assertElementDisplayed(headerTitle);
    }

    public void validateCartIconDisplayed() {
        assertElementDisplayed(cartIcon);
    }

    public void validateHamburgerMenuDisplayed() {
        assertElementDisplayed(headerTitle);
    }

    public void validateCartBadgeQty(Integer expectedQuantity){
        waitUntilDisplayed(cartBadgeQty);
        Integer actualBadgeQty = Integer.parseInt(cartBadgeQty.getText());
        Assert.assertEquals(actualBadgeQty,expectedQuantity,"Cart Badge Quantity is not Equals");
    }

    public void clickShoppingCartIcon() {
        click(cartIcon);
    }

    public void openMenu() {
        click(menuButton);
    }

    public void clickLogoutLink() {
        openMenu();
        click(logoutLink);
    }
}
