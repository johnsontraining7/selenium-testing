package com.johnsontraining.flipkart.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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

	public void loadProperties() {

		properties = new Properties();
		FileReader reader;
		try {
			reader = new FileReader(new File("src/test/resources/config.properties"));
			properties.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {

		if (properties == null) {
			loadProperties();
		}
		return properties.getProperty(key);
	}

	public WebDriver getDriver() {

		String driverToChooseForThisExecution = getProperty("driver");
		if (driverToChooseForThisExecution.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (driverToChooseForThisExecution.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (driverToChooseForThisExecution.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else {
			System.out.println("Launching chrome driver as default");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

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
		actions.click(element).keyDown(Keys.SHIFT).sendKeys(stringToEnter).keyUp(Keys.SHIFT).sendKeys(Keys.ENTER)
				.perform();
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

		for (int i = 0; i < list.size(); i++) {
			System.out.println(String.format("%s item in the list is -> %s", i, list.get(i)));
		}
	}

	public void closeDriver() {

		driver.close();
	}

	public void writeToExcel(List<String> productList) throws IOException {

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("userDetails");// workbook.getSheetAt(0);
		Row row = sheet1.createRow(0); // sheet1.getRow(i);
		row.createCell(0).setCellValue("User");
		row.createCell(1).setCellValue("Mobile");
		for (int i = 1; i <= productList.size(); i++) {
			Row currentRow = sheet1.createRow(i);
			currentRow.createCell(0).setCellValue(productList.get(i - 1));
		}

		FileOutputStream fos = new FileOutputStream(new File("src/test/resources/generated-product-data.xlsx"));
		workbook.write(fos);

		workbook.close();
		fos.close();
	}

	public ResultSet executeDbQuery() {

//		Map<Integer, Map<String, Object>> allUsersMap = new LinkedHashMap<Integer, Map<String, Object>>();
//		String dbUrl = "jdbc:mysql://127.0.0.1:3036/training";
//		String dbUserName = "root";
//		String dbPassWord = "pass1234";

		// Step-1 : Choose the right driver for the db you've chosen and initialize the
		// driver.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Step-2 : Using the driver manager establish a db-connection.
		Connection connection;
		Statement statement;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(getProperty("dbUrl"), getProperty("dbUserName"),
					getProperty("dbPassWord"));
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM training.user;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	public Map<String, Map<String, Object>> readExcelFileUsingMap() throws IOException {

		Map<String, Map<String, Object>> allUsersMap = new LinkedHashMap<String, Map<String, Object>>();

		FileInputStream fis = new FileInputStream(new File("src/test/resources/testdata.xlsx"));

		Workbook workbook = new XSSFWorkbook(fis);// XSSF -> Xml-Spread-Sheet-Format
//		Workbook workbook2 = new HSSFWorkbook(fis);// HSSF -> Horrible-Spread-Sheet-Format

//		Sheet sheet1 = workbook.getSheet("Sheet1");
		Sheet sheet1 = workbook.getSheetAt(0);

		// header-row
		Row headerRow = sheet1.getRow(0);

		for (int i = 1; i <= sheet1.getLastRowNum(); i++) { // Iterates thru every row
			Map<String, Object> currentUserMap = new HashMap<String, Object>();

			Row currentRow = sheet1.getRow(i); // stores the current row
			for (int j = 1; j < currentRow.getLastCellNum(); j++) { // iterate thru every cell in the current row

				if (currentRow.getCell(j).getCellType().name().equals(CellType.STRING.name())) {
					currentUserMap.put(headerRow.getCell(j).getStringCellValue(),
							currentRow.getCell(j).getStringCellValue());
//					System.out.println(currentRow.getCell(j).getStringCellValue());
				} else if (currentRow.getCell(j).getCellType().name().equals(CellType.NUMERIC.name())) {
					currentUserMap.put(headerRow.getCell(j).getStringCellValue(),
							currentRow.getCell(j).getNumericCellValue());
//					System.out.println(currentRow.getCell(j).getNumericCellValue());
				}
			}
			allUsersMap.put(currentRow.getCell(0).getStringCellValue(), currentUserMap);
		}

		for (Map.Entry<String, Map<String, Object>> entry : allUsersMap.entrySet()) {
			System.out.println(String.format("Key: %s, value: %s", entry.getKey(), entry.getValue()));
//			System.out.println("Key: " + entry.getKey() + ", value: " + entry.getValue());
		}

		fis.close();
		return allUsersMap;
	}
	
	public Map<String, Map<String, Object>> readExcelFileUsingMap(String fileName) throws IOException {

		Map<String, Map<String, Object>> allUsersMap = new LinkedHashMap<String, Map<String, Object>>();

		FileInputStream fis = new FileInputStream(new File("src/test/resources/" + fileName));

		Workbook workbook = new XSSFWorkbook(fis);// XSSF -> Xml-Spread-Sheet-Format
//		Workbook workbook2 = new HSSFWorkbook(fis);// HSSF -> Horrible-Spread-Sheet-Format

//		Sheet sheet1 = workbook.getSheet("Sheet1");
		Sheet sheet1 = workbook.getSheetAt(0);

		// header-row
		Row headerRow = sheet1.getRow(0);

		for (int i = 1; i <= sheet1.getLastRowNum(); i++) { // Iterates thru every row
			Map<String, Object> currentUserMap = new HashMap<String, Object>();

			Row currentRow = sheet1.getRow(i); // stores the current row
			for (int j = 1; j < currentRow.getLastCellNum(); j++) { // iterate thru every cell in the current row

				if (currentRow.getCell(j).getCellType().name().equals(CellType.STRING.name())) {
					currentUserMap.put(headerRow.getCell(j).getStringCellValue(),
							currentRow.getCell(j).getStringCellValue());
//					System.out.println(currentRow.getCell(j).getStringCellValue());
				} else if (currentRow.getCell(j).getCellType().name().equals(CellType.NUMERIC.name())) {
					currentUserMap.put(headerRow.getCell(j).getStringCellValue(),
							currentRow.getCell(j).getNumericCellValue());
//					System.out.println(currentRow.getCell(j).getNumericCellValue());
				}
			}
			allUsersMap.put(currentRow.getCell(0).getStringCellValue(), currentUserMap);
		}

		for (Map.Entry<String, Map<String, Object>> entry : allUsersMap.entrySet()) {
			System.out.println(String.format("Key: %s, value: %s", entry.getKey(), entry.getValue()));
//			System.out.println("Key: " + entry.getKey() + ", value: " + entry.getValue());
		}

		fis.close();
		return allUsersMap;
	}
	
	public void selectCheckbox(String checkboxXpath, boolean selectStatus) {
		
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
	
	public void handlePopup(String popUpXpath) {

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
}
