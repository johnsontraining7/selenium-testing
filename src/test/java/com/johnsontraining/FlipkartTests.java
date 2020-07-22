package com.johnsontraining;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.johnsontraining.flipkart.pages.BasePage;
import com.johnsontraining.flipkart.pages.HomePage;
import com.johnsontraining.flipkart.pages.LoginPage;

public class FlipkartTests {
	
	private WebDriver driver;
	private LoginPage loginpage;
	private HomePage homepage;
	private BasePage basePage;
	
	@BeforeClass
	public void setUp() {
		
		basePage = new BasePage(driver);
		driver = basePage.getDriver();
		loginpage = new LoginPage(driver);
		homepage = new HomePage(driver);
	}
	
	@Test
	public void closeLoginPopup() {
		
		loginpage.navigateTo("https://www.flipkart.com");
		
		loginpage.closeLoginPage();
	}
	
	@Test
	public void searchFromElectronics() {
		
		loginpage.navigateTo("https://www.flipkart.com");
		
		loginpage.closeLoginPage();
		
		homepage.selectSamsungOption();
		
		//validate if you're on samsung product listing page
	}
	
	@Test
	public void searchFromSearchBox() {
		
		loginpage.navigateTo("https://www.flipkart.com");
		
		loginpage.closeLoginPage();
		
		homepage.searchFor("iphone");
		
		//validate if you're on samsung product listing page
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
