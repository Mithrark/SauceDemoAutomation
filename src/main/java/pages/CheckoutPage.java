package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		this.driver=driver;
	}
	
	By Cart = By.className("shopping_cart_link");
	By CheckoutButton = By.id("checkout");
	By FirstName = By.name("firstName");
	By LastName = By.name("lastName");
	By ZipPostalCode = By.name("postalCode");
	By ContinueButton = By.id("continue");
	By FinishButton = By.id("finish");
	By OrderCompleteText = By.className("complete-header");
	
	public void ClickCart() {
		driver.findElement(Cart).click();
	}
	
	public void ClickCheckout() {
		driver.findElement(CheckoutButton).click();
	}
	
	public void FillInfo(String First_Name, String Last_Name, String Zip_Postal_Code) {
		
		driver.findElement(FirstName).sendKeys(First_Name);
		driver.findElement(LastName).sendKeys(Last_Name);
		driver.findElement(ZipPostalCode).sendKeys(Zip_Postal_Code);
		
		driver.findElement(ContinueButton).click();
		
	}
	
	public void ClickFinish() {
		driver.findElement(FinishButton).click();
	}
	
	public String OrderCompleteText() {
		return driver.findElement(OrderCompleteText).getText();
	}
}

