package com.johnsontraining.flipkart;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.johnsontraining.flipkart.pages.BasePage;
import com.johnsontraining.flipkart.pages.HomePage;
import com.johnsontraining.flipkart.pages.LoginPage;
import com.johnsontraining.flipkart.pages.ProductListingPage;

public class FlipkartTests {
	
	private WebDriver driver;
	private LoginPage loginpage;
	private HomePage homepage;
	private BasePage basePage;
	private ProductListingPage productListingPage;
	
	@BeforeClass
	public void setUp() {
		
		basePage = new BasePage(driver);
		driver = basePage.getDriver();
		loginpage = new LoginPage(driver);
		homepage = new HomePage(driver);
		productListingPage = new ProductListingPage(driver);
	}
	
	@Test
	public void closeLoginPopup() {
		
		Reporter.log("Started the close login popup test", true);
		loginpage.navigateTo("https://www.flipkart.com");
		Reporter.log("Navigating to the homepage of the application", true);
		loginpage.closeLoginPage();
		Reporter.log("Closed the login popup", true);
		Reporter.log("Ended the close login popup test", true);
	}
	
	@Test
	public void searchFromElectronics() {
		
		loginpage.navigateTo("https://www.flipkart.com");
		
		loginpage.closeLoginPage();
		
		homepage.selectSamsungOption();
		
		//validate if you're on samsung product listing page
	}
	
	@Test
	public void searchFromSearchBox() throws IOException {
		
		loginpage.navigateTo("https://www.flipkart.com");
		
		loginpage.closeLoginPage();
		
		homepage.searchFor("iphone");
		
		List<String> allProducts = productListingPage.getAllProducts();
		Assert.assertEquals(allProducts.size(), 40);
		
		productListingPage.printElements(allProducts);
		
		productListingPage.writeToExcel(allProducts);
	}
	
	@Test
	public void performLogin() {
		
		loginpage.navigateTo("https://www.flipkart.com");
		loginpage.login();
	}
	
	@Test(dataProvider = "loginData")
	public void performLoginWith(String username, String password) {
		
		loginpage.navigateTo("https://www.flipkart.com");
		loginpage.login(username, password);
	}
	
	@AfterClass
	public void tearDown() {
		
		basePage.closeDriver();
	}
	
	@DataProvider
	public Object[][] loginData(){
		
		return new Object[][] {{"Invalid username", "Invalid password"}, {"username", "Invalid password"}, {"Invalid username", "password"}};
	}

}
