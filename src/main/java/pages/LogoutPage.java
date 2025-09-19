package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogoutPage {


	WebDriver driver;

	public LogoutPage(WebDriver driver) {
		this.driver=driver;
	}

	By BackHome = By.id("back-to-products");
	By OpenMenu = By.className("bm-burger-button");
	By Logout = By.id("logout_sidebar_link");
	
	public void BackHomebutton() {
		driver.findElement(BackHome).click();
	}
	
	public void Logout() {
		driver.findElement(OpenMenu).click();
		driver.findElement(Logout).click();
	}
}
