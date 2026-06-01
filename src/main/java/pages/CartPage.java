package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

public class CartPage extends BasePage {

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage() {
        super();
    }

    public List<String> getCartProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement item : cartItems) {
            names.add(item.getText());
        }
        return names;
    }

    public void checkOutNext() {
        checkoutButton.click();
    }
}
