package tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.LogoutPage;
import pages.ProductsPage;
import utils.BaseTest;

public class LoginTest extends BaseTest {
	LoginPage login;
	BaseTest baseTest;

	ProductsPage productsPage;
	CartPage cartPage;
	CheckoutPage checkoutPage;
	LogoutPage logoutPage;

	@BeforeMethod
	public void SetupPages() {

		login = new LoginPage(driver);
		productsPage = new ProductsPage(driver);
		cartPage = new CartPage(driver);
		checkoutPage = new CheckoutPage(driver);
		logoutPage = new LogoutPage(driver);

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
	
	@Test
	public void TC_Checkout_03_Validate_Order_Checkout() {
		
		loginAndGetProducts();
		
		//Get Item name and price from homepage
		// Select products by name only
		String[] selectedProducts = {"Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket"};
		cartPage.AddMultipleItems(selectedProducts);

		// Get product map (name → price)
		Map<String, Double> productMap = productsPage.getProductsWithPrices();

		// Extract only the selected prices
		Double[] selectedPrices = new Double[selectedProducts.length];
		for (int i = 0; i < selectedProducts.length; i++) {
		    selectedPrices[i] = productMap.get(selectedProducts[i]);
		}

		String SelectedProductNames = Arrays.toString(selectedProducts);
		String SelectedProductPrices = Arrays.toString(selectedPrices);		
		
		System.out.println("Selected Product Names : "+SelectedProductNames);
		System.out.println("Selected Product Prices : "+SelectedProductPrices);
		
		//Click on Cart
		checkoutPage.ClickCart();

		//Click on Checkout
		checkoutPage.ClickCheckout();

		//Fill Info and click on continue
		checkoutPage.FillInfo("Mithra", "R K", "600273");		
		
		List<Double> ProductPrices = productsPage.getPrices();
		
		//Validate Product Name, prices
		String Checkout_ProductNames = productsPage.getProductNames().toString();
		String Checkout_ProductPrices = ProductPrices.toString();
		
		//System.out.println("Checkout_ProductPrices: "+ Checkout_ProductPrices);
		
		Assert.assertEquals(Checkout_ProductNames, SelectedProductNames);
		Assert.assertEquals(Checkout_ProductPrices, SelectedProductPrices);
		
		System.out.println("Product Names in checkout page : "+Checkout_ProductNames);
		System.out.println("Product Prices in checkout page : "+Checkout_ProductPrices);
		
		//check Item total -> sum of items price
		
		double total = 0.0;
		for (Double price : ProductPrices) {
		    total += price;
		}
		//System.out.println("Total: " + total);
		
		Assert.assertEquals(checkoutPage.Item_Total(), String.format("%.2f", total));
		System.out.println("Item Total is $"+checkoutPage.Item_Total());
		
		//Validate if Tax is 8% of Item Total
		
		double Item_total= Double.parseDouble(checkoutPage.Item_Total());
		String ExpectedTax = String.format("%.2f", Item_total*0.08);
		
		Assert.assertEquals(checkoutPage.Tax(), ExpectedTax );
		System.out.println("Tax is $"+checkoutPage.Tax());
		
		//Validate Total = Item Total + tax
		
		double ExpectedTotal = (Double.parseDouble(String.format("%.2f", total))) + (Double.parseDouble(ExpectedTax));		
		
		Assert.assertEquals(Double.parseDouble(checkoutPage.Total()), ExpectedTotal);
		System.out.println("Total is $"+ExpectedTotal);
	}
	
	@Test
	public void TC_Account_01_NavigateBackHome() throws InterruptedException {
		
		TC_Checkout_01_SuccessfulCheckout();
		
		//click Back Home
		logoutPage.BackHomebutton();
		
		//Validate if redirected to home page
		Assert.assertTrue(productsPage.getProductCount() >=6);
	}
	
	@Test
	public void TC_Account_02_Logout() throws InterruptedException {
		
		TC_Account_01_NavigateBackHome();
		
		logoutPage.Logout();
		
		login.LoginButtoncheck();
	}
}

