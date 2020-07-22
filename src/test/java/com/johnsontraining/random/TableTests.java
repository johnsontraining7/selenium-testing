package com.johnsontraining.random;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TableTests {
	
	private WebDriver driver;

	@BeforeClass
	public void setUp() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	
	@Test
	public void checkBoxTest() throws InterruptedException {

		System.out.println("checkBoxTest started");
		
		driver.navigate().to("https://www.seleniumeasy.com/test/table-data-download-demo.html");
		
		List<WebElement> headerElements = driver.findElements(By.xpath("//th"));
		Map<String, List<Object>> userMap = new LinkedHashMap<String, List<Object>>();
		
		for(int i=0; i<headerElements.size(); i++) {
			System.out.println(headerElements.get(i).getText());
			List<WebElement> allColumnDetails = driver.findElements(By.xpath("//tbody/tr/td["+(i+1)+"]"));
			List<Object> listOfCurrentColumn = new ArrayList<Object>();
			for(WebElement element : allColumnDetails) {
				listOfCurrentColumn.add(element.getText());
			}
			userMap.put(headerElements.get(i).getText(), listOfCurrentColumn);
		}
		
		System.out.println("Name: " + userMap.get("Name").get(0) + " salary is " + userMap.get("Salary").get(0));
		
	}

}
