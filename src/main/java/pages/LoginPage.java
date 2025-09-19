package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	public WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}

	By username=By.id("user-name");
	By password=By.id("password");
	By loginButton=By.id("login-button");
	By errorMsg=By.cssSelector("h3[data-test='error']");
	
	
	public void enterUsername(String user_name) {
		driver.findElement(username).sendKeys(user_name);
	}
	
	public void enterPassword(String pwd) {
		driver.findElement(password).sendKeys(pwd);
	}
	
	public void clickLogin() {
		driver.findElement(loginButton).click();
	}
	
	public void LoginToApp(String user_name,String pwd) {
		enterUsername(user_name);
		enterPassword(pwd);
		clickLogin();
	}
	
	public String getErrorMessage() {
		return driver.findElement(errorMsg).getText();
	}
	
	public void LoginButtoncheck() {
		driver.findElement(loginButton).isDisplayed();
	}
}
