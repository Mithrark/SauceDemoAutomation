package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver=driver;
	}

	//Add to cart/remove button
	String pricebar_AddToCart = "//div[text()='%s']/../../../div[@class='pricebar']/button";

	By CartText = By.className("shopping_cart_badge");

	String ProductNameLocator = "//div[@class='inventory_item_name '][text()='%s']";

	By ProductTitle = By.className("inventory_details_name");
	By ProductDescription = By.className("inventory_details_desc");

	By AddToCartButton = By.name("add-to-cart");

	private By getAddToCartButton(String productName) {
		return By.xpath(String.format(pricebar_AddToCart, productName));
	}

	public String ButtonText(String ProductName) {	
		return driver.findElement(getAddToCartButton(ProductName)).getText();	
	}

	public void AddtoCart(String ProductName) {			
		driver.findElement(getAddToCartButton(ProductName)).click();		
	}

	public String CartCount() {
		return driver.findElement(CartText).getText();

	}
	
	public void ClickCart() {
		driver.findElement(CartText).click();
	}

	public void ClickProduct(String ProductName) {

		By clickingProduct = By.xpath(String.format(ProductNameLocator, ProductName));

		driver.findElement(clickingProduct).click();
	}

	public String getTitle() {

		return driver.findElement(ProductTitle).getText();
	}

	public String getDescription() {

		return driver.findElement(ProductDescription).getText();
	}

	public void ClickAddToCart() {

		driver.findElement(AddToCartButton).click();
	}

	public void AddMultipleItems(String ProductNames[]) {

		for(String name:ProductNames) {
			AddtoCart(name);
		}
	}
	
	// Check if a product is present in the cart
	public boolean isProductInCart(String productName) {
	    return !driver.findElements(By.xpath("//div[@class='cart_item']//div[text()='" + productName + "']")).isEmpty();
	}
	
}
