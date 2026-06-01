package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import base.BasePage;

public class CheckoutInfoPage extends BasePage {

    @FindBy(id = "continue")
    private WebElement continueButton;

    public CheckoutInfoPage() {
        super();
    }

    public enum Checkout {
        FIRSTNAME("first-name"),
        LASTNAME("last-name"),
        POSTAL_CODE("postal-code");

        private final String id;
        Checkout(String id) {
            this.id = id;
        }
    }

    public void checkOut(Checkout checkout, String info) {
        getDriver().findElement(By.id(checkout.id)).sendKeys(info);
    }

    public void proceed() {
        continueButton.click();
    }
}
