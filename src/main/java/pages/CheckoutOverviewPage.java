package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(className = "inventory_item_name")
    private List<WebElement> inventoryList;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement confirmationHeader;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    public CheckoutOverviewPage() {
        super();
    }

    public List<String> getCheckOutDetails() {
        List<String> names = new ArrayList<>();
        for (WebElement item : inventoryList) {
            names.add(item.getText());
        }
        return names;
    }

    public void finish() {
        finishButton.click();
    }

    public String getConfirmationHeader() {
        return confirmationHeader.getText();
    }

    public String getConfirmationText() {
        return confirmationText.getText();
    }
}
