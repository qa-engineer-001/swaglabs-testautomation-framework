package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import base.BasePage;

public class ProductsPage extends BasePage {

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    public ProductsPage() {
        super();
    }

    public enum Product {
        BACKPACK("add-to-cart-sauce-labs-backpack"),
        BIKE_LIGHT("add-to-cart-sauce-labs-bike-light"),
        BOLT_TSHIRT("add-to-cart-sauce-labs-bolt-t-shirt"),
        FLEECE_JACKET("add-to-cart-sauce-labs-fleece-jacket"),
        ONESIE("add-to-cart-sauce-labs-onesie"),
        RED_TSHIRT("add-to-cart-test.allthethings()-t-shirt-(red)");

        private final String id;
        Product(String id) {
            this.id = id;
        }
    }

    public void selectProduct(Product product) {
        getDriver().findElement(By.id(product.id)).click();
    }

    public void clickOnCart() {
        cartIcon.click();
    }
}
