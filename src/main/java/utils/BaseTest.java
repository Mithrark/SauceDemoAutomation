package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;

	@BeforeMethod
	public void setup(){
		//WebDriverManager.edgedriver().setup();
		//System.setProperty("webdriver.edge.driver", "C:\\Users\\mithra.r.k\\Eclipse\\eclipse-workspace\\Common_Steps_with_Sedgwick_Reference-19March\\src\\main\\resources\\msedgedriver.exe");
		//driver=new EdgeDriver();
		
		ChromeOptions options = new ChromeOptions();
		String userProfile = "C:/Temp/ChromeProfile_" + System.currentTimeMillis();
		options.addArguments("--user-data-dir=" + userProfile);
		driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
	}
	
	@AfterMethod
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }
}
