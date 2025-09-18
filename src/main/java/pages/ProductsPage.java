package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductsPage {

	WebDriver driver;

	public ProductsPage(WebDriver driver) {
		this.driver=driver;
	}

	By productCount = By.cssSelector("div[class='inventory_item']");
	By productSort=By.className("product_sort_container");
	By productPrices= By.className("inventory_item_price");	
	By productNames= By.className("inventory_item_name");

	public int getProductCount() {
		return driver.findElements(productCount).size();

	}

	//sort any value
	public void sortBy(String selectValue) {
		WebElement dropdown = driver.findElement(productSort); 
		Select priceSort= new Select(dropdown);
		priceSort.selectByVisibleText(selectValue);
	}

	//get all the prices in the page
	public List<Double> getPrices() {

		List<WebElement> priceElements= driver.findElements(productPrices);

		List<Double> prices=new ArrayList<>();

		for (WebElement priceElement : priceElements) {

			prices.add(Double.parseDouble(priceElement.getText().replace("$", "")));
		}

		return prices;
	}



	// Check if prices are sorted Low to High
	public boolean isPriceSortedLowToHigh() {
		List<Double> actualPrices = getPrices();
		List<Double> sortedPrices = new ArrayList<>(actualPrices);

		Collections.sort(sortedPrices);
		return actualPrices.equals(sortedPrices);
	}

	//sort Name A-Z
	// Get all product names as text
	public List<String> getProductNames() {

		List<WebElement> nameElements = driver.findElements(productNames);
		List<String> names = new ArrayList<>();

		for (WebElement nameElement : nameElements) {     
			names.add(nameElement.getText());
		}

		return names;
	}

	// check if sorted from A to Z
	public boolean isNameSortedAToZ() {
		List<String> actualNames = getProductNames();
		List<String> sortedNames = new ArrayList<>(actualNames);

		Collections.sort(sortedNames, String.CASE_INSENSITIVE_ORDER);
		return actualNames.equals(sortedNames);
	}

	public Map<String, Double> getProductsWithPrices() {
		List<WebElement> nameElements = driver.findElements(productNames);
		List<WebElement> priceElements = driver.findElements(productPrices);

		Map<String, Double> productMap = new HashMap<>();

		for (int i = 0; i < nameElements.size(); i++) {
			String name = nameElements.get(i).getText();
			Double price = Double.parseDouble(priceElements.get(i).getText().replace("$", ""));
			productMap.put(name, price);
		}

		return productMap;
	}




	//validate product name and price in checkout page

	//   public void Checkout_ProductName() {
	// 	productNames
	//}
}


