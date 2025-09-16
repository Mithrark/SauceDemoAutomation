package tests;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.BaseTest;

public class LoginTest extends BaseTest {
	LoginPage login;
	BaseTest baseTest;

	ProductsPage productsPage;
	CartPage cartPage;
	CheckoutPage checkoutPage;

	@BeforeMethod
	public void SetupPages() {

		login = new LoginPage(driver);
		productsPage = new ProductsPage(driver);
		cartPage = new CartPage(driver);
		checkoutPage = new CheckoutPage(driver);

		//Login with valid credentials (standard_user)
		//login.LoginToApp("standard_user", "secret_sauce");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	private String[] loginAndGetProducts() {
		// Perform login
		login.LoginToApp("standard_user", "secret_sauce");

		// Initializing pages
		productsPage = new ProductsPage(driver);
		cartPage = new CartPage(driver);

		// Fetch product names
		return productsPage.getProductNames().toArray(new String[0]);
	}

/*
	@Test public void TC_Login_01_validLogin() {

		//Login with valid credentials (standard_user)
		login.LoginToApp("standard_user", "secret_sauce");

		//Navigates to Inventory page 
		InventoryPage inventory = new InventoryPage(driver); 
		String title = inventory.getTitle();
		Assert.assertEquals(title, "Products"); }

	@Test public void TC_Login_02_invalidLogin() {

		//Login with invalid credentials 
		login.LoginToApp("standard_user", "test_wrong");

		//Error message displayed 
		Assert.assertTrue(login.getErrorMessage().contentEquals("Epic sadface: Username and password do not match any user in this service")); 

	}

	@Test public void TC_Login_03_emptyFields() {

		//Login with empty values 
		login.LoginToApp("", "");

		//Error required fields 
		Assert.assertTrue(login.getErrorMessage().contains("Epic sadface: Username is required")); }

	@Test public void TC_Login_04_lockedOutUser() {

		//Login with locked-out user 
		login.LoginToApp("locked_out_user","secret_sauce");

		//Lockout message 
		Assert.assertTrue(login.getErrorMessage().contains("Epic sadface: Sorry, this user has been locked out.")); }

	@Test public void TC_Product_01_productsVisbile() {

		//Login with valid credentials (standard_user)
		login.LoginToApp("standard_user", "secret_sauce");

		Assert.assertTrue(productsPage.getProductCount() >=6);

	}

	@Test public void TC_Product_02_sortFunctionality() throws
	InterruptedException {

		//Login with valid credentials (standard_user)
		login.LoginToApp("standard_user", "secret_sauce");

		Thread.sleep(3000); 
		productsPage.sortBy("Price (low to high)");
		Assert.assertTrue(productsPage.isPriceSortedLowToHigh());

		productsPage.sortBy("Name (A to Z)");
		Assert.assertTrue(productsPage.isNameSortedAToZ());


	}


	@Test public void TC_Cart_01_addToCart() throws InterruptedException { 

		String[] ProductNames = loginAndGetProducts();

		//for(String name : ProductNames) {
		//System.out.println("Product Names: "+name); }

		Assert.assertEquals(cartPage.ButtonText(ProductNames[1]),"Add to cart");
		cartPage.AddtoCart(ProductNames[1]);
		Assert.assertEquals(cartPage.ButtonText(ProductNames[1]), "Remove");
		Assert.assertEquals(cartPage.CartCount(), "1");

	}

	@Test public void TC_Cart_02_viewProduct() throws InterruptedException {

		String[] ProductNames = loginAndGetProducts();

		cartPage.ClickProduct(ProductNames[1]);

		//verify title
		Assert.assertTrue(cartPage.getTitle().contains(ProductNames[1]));

		//verify description 
		Assert.assertTrue(cartPage.getDescription().length()>0);

		//click on Add to cart 
		cartPage.ClickAddToCart(); 
	}

	@Test public void TC_Cart_03_AddMultipleItems() throws InterruptedException {

		String[] ProductNames = loginAndGetProducts();

		String[] ProductsToAdd = {ProductNames[0], ProductNames[2], ProductNames[3]};
		cartPage.AddMultipleItems(ProductsToAdd);

		Assert.assertEquals(Integer.parseInt(cartPage.CartCount()),
				ProductsToAdd.length);

	}


	@Test
	public void TC_Cart_04_RemoveItemFromCart() throws InterruptedException {

		String[] ProductNames = loginAndGetProducts();

		// Add products
		String[] ProductsToAdd = { ProductNames[0], ProductNames[2], ProductNames[3] };
		cartPage.AddMultipleItems(ProductsToAdd);

		// Remove product
		String[] ProductsToRemove = {ProductNames[2]};
		cartPage.AddMultipleItems(ProductsToRemove);

		// check count decreased
		Assert.assertEquals(Integer.parseInt(cartPage.CartCount()), ProductsToAdd.length-ProductsToRemove.length);

		//click on cart
		cartPage.ClickCart();

		//check if ProductsToRemove is present
		Assert.assertFalse(cartPage.isProductInCart(ProductsToRemove[0]),"Removed product '" + ProductsToRemove[0] + "' is still present in the cart!");

	}
*/
	@Test
	public void TC_Checkout_01_SuccessfulCheckout() throws InterruptedException {

		String[] ProductNames = loginAndGetProducts();

		//Add Items
		String[] ProductsToAdd = {ProductNames[0], ProductNames[2], ProductNames[3]};
		cartPage.AddMultipleItems(ProductsToAdd);

		//Click on Cart
		checkoutPage.ClickCart();

		//Click on Checkout
		checkoutPage.ClickCheckout();

		//Fill Info and click on continue
		checkoutPage.FillInfo("Mithra", "R K", "600273");

		//Click Finish
		checkoutPage.ClickFinish();

		System.out.println("checkout final msg : "+checkoutPage.OrderCompleteText());

		Assert.assertEquals(checkoutPage.OrderCompleteText(), "Thank you for your order!");


	}

	@Test
	public void TC_Checkout_02_CheckoutwithMissingInfo() {

		String[] ProductNames = loginAndGetProducts();

		//Add Items
		String[] ProductsToAdd = {ProductNames[0], ProductNames[1], ProductNames[3]};
		cartPage.AddMultipleItems(ProductsToAdd);

		//Click on Cart
		checkoutPage.ClickCart();

		//Click on Checkout
		checkoutPage.ClickCheckout();

		String firstName = "Mithra";
	    String lastName  = "";
	    String postal    = ""; 
		
		//Fill Info and click on continue
		checkoutPage.FillInfo(firstName, lastName, postal);

		System.out.println("checkout Page Error msg : "+checkoutPage.ErrorText());
		
		String expectedError = "";
				
		if(firstName.isEmpty()) {
			expectedError = "Error: First Name is required";
	    } else if(lastName.isEmpty()) {
	        expectedError = "Error: Last Name is required";
	    } else if(postal.isEmpty()) {
	        expectedError = "Error: Postal Code is required";
	    }

		//Assert.assertEquals(checkoutPage.ErrorText(), "Error: Postal Code is required");
		
		//Assert.assertTrue(checkoutPage.ErrorText().startsWith("Error:"));
		
		Assert.assertEquals(checkoutPage.ErrorText(), expectedError);
	}
}

