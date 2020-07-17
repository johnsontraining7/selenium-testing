package com.johnsontraining.flipkart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {
	
	public WebDriver driver;
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		return driver;
	}
	
	public void navigateTo(String url) {
	
		driver.navigate().to(url);
}
	
//	public void clickElement(WebElement element) {
//		
//		element.click();
//	}

}
