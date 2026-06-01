package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutomationScript {

	public static WebDriver driver;

	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

		driver.get("https://www.saucedemo.com/");
	}

	@Test
	public void automationTest() {

		//Login
		WebElement userName = driver.findElement(By.id("user-name"));
		userName.sendKeys("standard_user");

		WebElement pwd = driver.findElement(By.id("password"));
		pwd.sendKeys("secret_sauce");

		driver.findElement(By.id("login-button")).click();

		//Products
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
		driver.findElement(By.id("shopping_cart_container")).click();

		//Your Cart
		driver.findElement(By.id("checkout")).click();

		//Checkout: Your Information
		WebElement fName = driver.findElement(By.id("first-name"));
		fName.sendKeys("standard");
		WebElement lName = driver.findElement(By.id("last-name"));
		lName.sendKeys("user");
		WebElement postCode = driver.findElement(By.id("postal-code"));
		postCode.sendKeys("12345");
		driver.findElement(By.id("continue")).click();

		//Checkout: Overview
		driver.findElement(By.id("finish")).click();

		//Checkout: Complete

	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}

}