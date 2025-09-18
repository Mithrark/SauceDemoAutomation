package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	By ErrorText = By.xpath("//*[@data-test=\"error\"]");

	By productPrices= By.className("inventory_item_price");	

	By ItemTotal = By.className("summary_subtotal_label");
	By Tax = By.className("summary_tax_label");
	By Total = By.className("summary_total_label");

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

	public String ErrorText() {
		return driver.findElement(ErrorText).getText();
	}

	public String Item_Total() {
		return driver.findElement(ItemTotal).getText().replace("Item total: $", "");
	}

	public String Tax() {
		return driver.findElement(Tax).getText().replace("Tax: $", "");
	}

	public String Total() {
		return driver.findElement(Total).getText().replace("Total: $", "");
	}
}

