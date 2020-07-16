package com.johnsontraining;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VariousSeleniumInteractions {

	private WebDriver driver;
	String popUpXpath = "//a[@title='Close']";

	@BeforeClass
	public void setUp() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test(priority = 1)
	public void textBoxTest() throws InterruptedException {

		System.out.println("textBoxTest started");
		// Test-1
		String expectedText = "Selenium is amazing!!!";

		driver.navigate().to("https://www.seleniumeasy.com/test/basic-first-form-demo.html");

		handlePopup(popUpXpath);
		
		WebElement messageTextBox = driver.findElement(By.xpath("//input[@id='user-message']"));

		messageTextBox.sendKeys(expectedText);

		driver.findElement(By.xpath("//button[text()='Show Message']")).click();

		String actualText = driver.findElement(By.xpath("//span[@id='display']")).getText();

		System.out.println("Actual : " + actualText + ", Expected : " + expectedText);
		Assert.assertEquals(actualText, expectedText, "Expected: " + expectedText + ", but found: " + actualText);
	}

	@Test(priority = 2)
	public void textBoxTest2() {

		System.out.println("textBoxTest2 started");
		
		String expectedText2 = "TestNG is amazing!!!";

		handlePopup(popUpXpath);

		driver.findElement(By.xpath("//input[@id='user-message']")).clear();
		driver.findElement(By.xpath("//input[@id='user-message']")).sendKeys(expectedText2);

		driver.findElement(By.xpath("//button[text()='Show Message']")).click();

		String actualText2 = driver.findElement(By.xpath("//span[@id='display']")).getText();

		Assert.assertEquals(actualText2, expectedText2, "Expected: " + expectedText2 + ", but found: " + actualText2);
	}
	
	@Test
	public void checkBoxTest() throws InterruptedException {

		System.out.println("checkBoxTest started");
		
		driver.navigate().to("https://www.seleniumeasy.com/test/basic-checkbox-demo.html");
		
		handlePopup(popUpXpath);

		selectCheckbox("//input[@id='isAgeSelected']", true);
		selectCheckbox("//input[@id='isAgeSelected']", true);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='txtAge']")).getText(), "Success - Check box is checked");
	}
	
	@Test
	public void dropdownTest() throws InterruptedException {

		System.out.println("checkBoxTest started");
		
		driver.navigate().to("https://www.seleniumeasy.com/test/basic-select-dropdown-demo.html");
		
		handlePopup(popUpXpath);

		selectDropdownByValue("//*[@id='select-demo']", "Thursday");
	}
	
	@Test
	public void multiDropdownTest() throws InterruptedException {

		System.out.println("checkBoxTest started");
		
		driver.navigate().to("https://www.seleniumeasy.com/test/basic-select-dropdown-demo.html");
		
		handlePopup(popUpXpath);

//		selectDropdownByValue("//*[@id='multi-select']", "Ohio");
//		selectDropdownByValue("//*[@id='multi-select']", "Texas");
//		selectDropdownByValue("//*[@id='multi-select']", "Florida");
		List<String> optionsToSelect = new ArrayList<String>();
		optionsToSelect.add("Florida");
		optionsToSelect.add("Ohio");
		optionsToSelect.add("Texas");
		
		Select select = createSelect("//*[@id='multi-select']");
		
		for(String currentOption : optionsToSelect) {
			selectOption(select, currentOption);
		}
		
		List<String> optionsSelected = new ArrayList<String>();
		List<WebElement> allSelectedOptions = select.getAllSelectedOptions();
		for(WebElement currentWebElement : allSelectedOptions) {
			optionsSelected.add(currentWebElement.getText());
		}
		
		Assert.assertEquals(optionsSelected, optionsToSelect);
	}
	
	@Test
	public void dropdownWithSearchBoxTest() throws InterruptedException {

		System.out.println("dropdownWithSearchBoxTest started");
		
		driver.navigate().to("https://www.seleniumeasy.com/test/jquery-dropdown-search-demo.html");
		
		handlePopup(popUpXpath);

		String valueToSelect = "India";
		selectDropdownByValue("//*[@id='country']", valueToSelect);
		
		System.out.println(getSelectedValueFromDropdown("//*[@id='country']"));
		Assert.assertEquals(getSelectedValueFromDropdown("//*[@id='country']"), valueToSelect);
	}
	
	private void selectDropdownByValue(String dropDownXpath, String valueToSelect) {
		Select select = new Select(driver.findElement(By.xpath(dropDownXpath)));
		select.selectByValue(valueToSelect);
	}
	
	private String getSelectedValueFromDropdown(String dropDownXpath) {
		Select select = new Select(driver.findElement(By.xpath(dropDownXpath)));
		return select.getFirstSelectedOption().getText();
	}
	
	private Select createSelect(String dropDownXpath) {
		Select select = new Select(driver.findElement(By.xpath(dropDownXpath)));
		return select;
	}
	
	private void selectOption(Select dropdown, String valueToSelect) {
		dropdown.selectByValue(valueToSelect);
	}
	
	private void selectDropdownByVisibleText(String dropDownXpath, String valueToSelect) {
		Select select = new Select(driver.findElement(By.xpath(dropDownXpath)));
		select.selectByVisibleText(valueToSelect);
	}
	
	private void selectCheckbox(String checkboxXpath, boolean selectStatus) {
		
		if(selectStatus) {
			if(driver.findElement(By.xpath(checkboxXpath)).isSelected()) {
				System.out.println("Checkbox is already selected");
			} else {
				System.out.println("Selecting the checkbox");
				driver.findElement(By.xpath(checkboxXpath)).click();
			}
		} else {
			if(driver.findElement(By.xpath(checkboxXpath)).isSelected()) {
				System.out.println("Disabling the checkbox");
				driver.findElement(By.xpath(checkboxXpath)).click();
			} else {
				System.out.println("Checkbox is already not selected");
			}
		}
	}

	private void handlePopup() {

		boolean status = true;
		int noOfTries = 0;
		while (status) {
			if(noOfTries++ < 30) {
				try {
					if(driver.findElement(By.xpath("//a[@title='Close']")).isDisplayed()) {
						driver.findElement(By.xpath("//a[@title='Close']")).click();
						status = false;
					}
				} catch (NoSuchElementException e) {}
			} else {
				System.out.println("No pop-up was seen in the UI, hence continuing with the test execution.");
				status = false;
			}
		}
	}
	
	private void handlePopup(WebElement element) {

		boolean status = true;
		int noOfTries = 0;
		while (status) {
			if(noOfTries++ < 30) {
				try {
					if(element.isDisplayed()) {
						element.click();
						status = false;
					}
				} catch (NoSuchElementException e) {}
			} else {
				System.out.println("No pop-up was seen in the UI, hence continuing with the test execution.");
				status = false;
			}
		}
	}
	
	private void handlePopup(String popUpXpath) {

		boolean status = true;
		int noOfTries = 0;
		System.out.println("Getting into a while loop");
		while (status) {
			
			if(noOfTries++ < 30) {
				System.out.println("No of tries-" + noOfTries + " is still less than max tries of 30");
				try {
					if(driver.findElement(By.xpath(popUpXpath)).isDisplayed()) {
						driver.findElement(By.xpath(popUpXpath)).click();
						status = false;
					}
				} catch (NoSuchElementException e) {}
			} else {
				System.out.println("No pop-up was seen in the UI, hence continuing with the test execution.");
				status = false;
			}
			System.out.println("No of tries is: " + noOfTries);
		}
	}

	@AfterClass
	public void tearDown() {

		driver.close();
	}
}
