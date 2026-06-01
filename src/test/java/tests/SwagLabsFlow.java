package tests;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.CartPage;
import pages.CheckoutInfoPage;
import pages.LoginPage;
import pages.ProductsPage;
import pages.ProductsPage.Product;
import utils.DataProviders;
import pages.CheckoutInfoPage.Checkout;
import pages.CheckoutOverviewPage;

public class SwagLabsFlow extends BaseTest{
	
	private static final Logger logger = LogManager.getLogger(SwagLabsFlow.class);
	
	@Test(dataProvider = "userData", dataProviderClass = DataProviders.class)
	public void automationFlow(String username, String password) {
		
		//Login
		LoginPage lg = new LoginPage();
		lg.enterUsername(username);
		lg.enterPassword(password);
		lg.clickLogin();
		logger.info("User: " +username+ " Logged in");
		getTest().log(Status.PASS, "User: " +username+ " Logged in");
		
		//SelectProduct
		ProductsPage sp = new ProductsPage();
		sp.selectProduct(Product.BACKPACK);
		sp.selectProduct(Product.BOLT_TSHIRT);
		logger.info("Products selected to Cart");
		getTest().log(Status.PASS, "Products selected to Cart");
		sp.clickOnCart();
		logger.info("Navigated to Cart");
		getTest().log(Status.PASS, "Navigated to Cart");
		
		//Cart
		CartPage cart = new CartPage();
		List<String> cartItems = cart.getCartProductNames();
		
		//validate number of items
		Assert.assertEquals(cartItems.size(), 2);
		logger.info("Validated number of items in Cart");
		getTest().log(Status.PASS, "Validated number of items in Cart");
		
		//validate products are present
		Assert.assertTrue(cartItems.contains("Sauce Labs Backpack"));
		Assert.assertTrue(cartItems.contains("Sauce Labs Bolt T-Shirt"));
		logger.info("Validated products in Cart: " + String.join(", ", cartItems));
		getTest().log(Status.PASS, "Validated products in Cart: " + String.join(", ", cartItems));
		
		cart.checkOutNext();
		logger.info("Proceed to Checkout");
		getTest().log(Status.PASS, "Proceed to Checkout");
		
		//Checkout: Your Information
		CheckoutInfoPage cyi = new CheckoutInfoPage();
		cyi.checkOut(Checkout.FIRSTNAME, "standard");
		cyi.checkOut(Checkout.LASTNAME, "user");
		cyi.checkOut(Checkout.POSTAL_CODE, "12345");
		cyi.proceed();
		logger.info("Filled checkout information and proceeded");
		getTest().log(Status.PASS, "Filled checkout information and proceeded");
		
		//Checkout: Overview
		CheckoutOverviewPage cop = new CheckoutOverviewPage();
		List<String> checkOutItems = cop.getCheckOutDetails();
		
		//validate number of items
		Assert.assertEquals(checkOutItems.size(), 2);
		logger.info("Validated number of items in Final Overview");
		getTest().log(Status.PASS, "Validated number of items in Final Overview");
		
		//validate products are present
		Assert.assertTrue(checkOutItems.contains("Sauce Labs Backpack"));
		Assert.assertTrue(checkOutItems.contains("Sauce Labs Bolt T-Shirt"));
		logger.info("Validated products in Final Overview: " + String.join(", ", checkOutItems));
		getTest().log(Status.PASS, "Validated products in Final Overview: " + String.join(", ", checkOutItems));
		
		cop.finish();
		logger.info("Clicked Finish");
		getTest().log(Status.PASS, "Clicked Finish");
		
		//Validate Confirmation
		Assert.assertEquals(cop.getConfirmationHeader(), "Thank you for your order!");
		Assert.assertEquals(cop.getConfirmationText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
		logger.info("Order confirmation validated successfully");
		getTest().log(Status.PASS, "Order confirmation validated successfully");		
		
	}

}