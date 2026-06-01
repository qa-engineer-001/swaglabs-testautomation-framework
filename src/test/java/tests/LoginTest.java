package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.DataProviders;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "userData", dataProviderClass = DataProviders.class)
    public void loginTest(String username, String password) {
        LoginPage lg = new LoginPage();
        lg.enterUsername(username);
        lg.enterPassword(password);
        lg.clickLogin();
    }
}
