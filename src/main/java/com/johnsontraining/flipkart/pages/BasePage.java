package com.johnsontraining.flipkart.pages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public WebDriver driver;
	public Properties properties;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {

//		String driverToChooseForThisExecution = properties.getProperty("driver");
//		if(driverToChooseForThisExecution.equalsIgnoreCase("chrome")) {
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//		} else if(driverToChooseForThisExecution.equalsIgnoreCase("firefox")) {
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//		} else if(driverToChooseForThisExecution.equalsIgnoreCase("ie")) {
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//		} else if(driverToChooseForThisExecution.equalsIgnoreCase("edge")) {
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//		} else {
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//		}
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		return driver;
	}

	public void navigateTo(String url) {

		driver.navigate().to(url);
	}
	
	public void waitForElementClickable(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForElementVisible(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementsVisible(List<WebElement> elements) {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	public void enterTextAndEnter(WebElement element, String stringToEnter) {
		
		waitForElementClickable(element);
		element.sendKeys(stringToEnter);
		element.sendKeys(Keys.ENTER);
	}
	
	public void enterTextUsingActionsInCaps(WebElement element, String stringToEnter) {
		
		Actions actions = new Actions(driver);
		actions.click(element).keyDown(Keys.SHIFT).sendKeys(stringToEnter).keyUp( Keys.SHIFT).sendKeys(Keys.ENTER).perform();
	}
	
	public void clickElement(WebElement element) {
		
		waitForElementVisible(element);
		element.click();
	}
	
	public void mouseHoverAndClick(WebElement element, WebElement subElement) {
		
		waitForElementVisible(element);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		
		waitForElementClickable(subElement);
		actions.click(subElement).build().perform();
	}
	
	public void printElements(List<String> list) {
		
		for(int i = 0; i < list.size(); i++) {
			System.out.println(String.format("%s item in the list is -> %s", i, list.get(i)));
		}
	}
	
	public void closeDriver(){
		
		driver.close();
	}
	
	public void writeToExcel(List<String> productList) throws IOException {
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("userDetails");// workbook.getSheetAt(0);
		Row row = sheet1.createRow(0); // sheet1.getRow(i);
		row.createCell(0).setCellValue("User");
		row.createCell(1).setCellValue("Mobile");
		for(int i = 1; i <= productList.size() ; i++) {
			Row currentRow = sheet1.createRow(i);
			currentRow.createCell(0).setCellValue(productList.get(i-1));
		}
		
		FileOutputStream fos = new FileOutputStream(new File("src/test/resources/generated-product-data.xlsx"));
		workbook.write(fos);
		
		workbook.close();
		fos.close();
	}
}
