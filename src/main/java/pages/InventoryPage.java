package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
	
	WebDriver driver;
	
	public InventoryPage(WebDriver driver) {
		this.driver=driver;
	}

	By title=By.className("title");
	
	public String getTitle() {
		return driver.findElement(title).getText();
	}
}
