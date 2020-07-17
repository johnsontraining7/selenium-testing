package com.johnsontraining;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Waits {
	
	private WebDriver driver;

	@BeforeClass
	public void setUp() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	@Test
	public void test1() throws InterruptedException {
		
		System.out.println("checkBoxTest started");
		
		driver.navigate().to("https://www.seleniumeasy.com/test/basic-checkbox-demo.html");

		selectCheckbox("//input[@id='isAgeSelected']", true);
		selectCheckbox("//input[@id='isAgeSelected']", true);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='txtAge']")).getText(), "Success - Check box is checked");
	}
	
	private void waitForElementClickable(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	private void waitForElementVisible(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	private void selectCheckbox(String checkboxXpath, boolean selectStatus) {
		
		waitForElementVisible(driver.findElement(By.xpath(checkboxXpath)));
		if(selectStatus) {
			if(driver.findElement(By.xpath(checkboxXpath)).isSelected()) {
				System.out.println("Checkbox is already selected");
			} else {
				System.out.println("Selecting the checkbox");
				waitForElementClickable(driver.findElement(By.xpath(checkboxXpath)));
				driver.findElement(By.xpath(checkboxXpath)).click();
			}
		} else {
			if(driver.findElement(By.xpath(checkboxXpath)).isSelected()) {
				System.out.println("Disabling the checkbox");
				waitForElementClickable(driver.findElement(By.xpath(checkboxXpath)));
				driver.findElement(By.xpath(checkboxXpath)).click();
			} else {
				System.out.println("Checkbox is already not selected");
			}
		}
	}

}
