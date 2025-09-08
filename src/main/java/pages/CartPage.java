package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver=driver;
	}

	//price bar where add to cart/remove is present - to check button change
	By pricebar = By.cssSelector("div[class='pricebar'] button");
	By BackpackCart = By.name("add-to-cart-sauce-labs-backpack");
	By CartText = By.className("shopping_cart_badge");
	
	String ProductNameLocator = "//div[@class='inventory_item_name '][text()='%s']";
	
	public String ButtonBeforeClick() {
		return driver.findElements(pricebar).get(0).getText();
	}
	
	public void AddtoCart() {
		driver.findElement(BackpackCart).click();
	}
	
	public String ButtonAfterClick() {
		return driver.findElements(pricebar).get(0).getText();
	}
	
	public String CartCount() {
		return driver.findElement(CartText).getText();
		
	}
	
	public void ClickProduct(String ProductName) {
		
		By clickingProduct = By.xpath(String.format(ProductNameLocator, ProductName));
		
		driver.findElement(clickingProduct).click();
	}

}
