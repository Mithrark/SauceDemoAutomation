package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MyTestCheck {

	static By productSort=By.className("product_sort_container");
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebDriver driver;

		ChromeOptions options = new ChromeOptions();
		String userProfile = "C:/Temp/ChromeProfile_" + System.currentTimeMillis();
		options.addArguments("--user-data-dir=" + userProfile);
		driver = new ChromeDriver(options);

		/*WebDriverManager.edgedriver().setup();
		System.setProperty("webdriver.edge.driver", "C:\\Users\\mithra.r.k\\Eclipse\\eclipse-workspace\\Common_Steps_with_Sedgwick_Reference-19March\\src\\main\\resources\\msedgedriver.exe");
		driver=new EdgeDriver();
*/

		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");

		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(productSort));

		//WebElement sortDropdown = driver.findElement(productSort);
		Select priceSort= new Select(sortDropdown);
		priceSort.selectByVisibleText("Price (low to high)");


	}

}
