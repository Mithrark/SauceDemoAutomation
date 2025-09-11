package tests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.BaseTest;

public class LoginTest extends BaseTest {
	LoginPage login;
	BaseTest baseTest;
	ProductsPage productsPage;
	CartPage cartPage;

	//String[] ProductNames = {"Sauce Labs Backpack","Sauce Labs Bike Light"};
	String[] ProductNames;
	
	@BeforeMethod
	public void SetupPages() {

		login = new LoginPage(driver);
		productsPage = new ProductsPage(driver);
		cartPage = new CartPage(driver);

		//Login with valid credentials (standard_user)
		login.LoginToApp("standard_user", "secret_sauce");
		
		ProductNames = productsPage.getProductNames().toArray(new String[0]);
	}
	/*
	@Test
	public void TC_Login_01_validLogin() {

		//Login with valid credentials (standard_user)
		login.LoginToApp("standard_user", "secret_sauce");

		//Navigates to Inventory page
		InventoryPage inventory = new InventoryPage(driver);
		String title = inventory.getTitle();
		Assert.assertEquals(title, "Products");
	}

	@Test
	public void TC_Login_02_invalidLogin() {

		//Login with invalid credentials
		login.LoginToApp("standard_user", "test_wrong");

		//Error message displayed
		Assert.assertTrue(login.getErrorMessage().contentEquals("Epic sadface: Username and password do not match any user in this service"));		
	}

	@Test
	public void TC_Login_03_emptyFields() {

		//Login with empty values
		login.LoginToApp("", "");

		//Error required fields
		Assert.assertTrue(login.getErrorMessage().contains("Epic sadface: Username is required"));
	}

	@Test
	public void TC_Login_04_lockedOutUser() {

		//Login with locked-out user
		login.LoginToApp("locked_out_user", "secret_sauce");

		//Lockout message
		Assert.assertTrue(login.getErrorMessage().contains("Epic sadface: Sorry, this user has been locked out."));
	}

	@Test
	public void TC_Product_01_productsVisbile() {

		//Login with valid credentials (standard_user)
		login.LoginToApp("standard_user", "secret_sauce");

		Assert.assertTrue(productsPage.getProductCount() >=6);

	}

	@Test
	public void TC_Product_02_sortFunctionality() throws InterruptedException {

		//Login with valid credentials (standard_user)
		login.LoginToApp("standard_user", "secret_sauce");

		Thread.sleep(3000);		
		productsPage.sortBy("Price (low to high)");
		Assert.assertTrue(productsPage.isPriceSortedLowToHigh());

		productsPage.sortBy("Name (A to Z)");
		Assert.assertTrue(productsPage.isNameSortedAToZ());


	}
	 */

	@Test
	public void TC_Cart_01_addToCart() {	
		/*for(String name : ProductNames) {
			System.out.println("Product Names: "+name);
		}*/
		
		Assert.assertEquals(cartPage.ButtonText(ProductNames[1]),"Add to cart");
		cartPage.AddtoCart(ProductNames[1]);
		Assert.assertEquals(cartPage.ButtonText(ProductNames[1]), "Remove");
		Assert.assertEquals(cartPage.CartCount(), "1");		
		
	}
	
	@Test
	public void TC_Cart_02_viewProduct() {
		
		cartPage.ClickProduct(ProductNames[1]);
		
		//verify title
		Assert.assertTrue(cartPage.getTitle().contains(ProductNames[1]));
		
		//verify description
		Assert.assertTrue(cartPage.getDescription().length()>0);
		
		//click on Add to cart
		cartPage.ClickAddToCart();
	}
	
	@Test
	public void TC_Cart_03_AddMultipleItems() {
		
		String[] ProductsToAdd = {ProductNames[0], ProductNames[2], ProductNames[3]};
		cartPage.AddMultipleItems(ProductsToAdd);
		
		Assert.assertEquals(Integer.parseInt(cartPage.CartCount()), ProductsToAdd.length);
		
	}
}
