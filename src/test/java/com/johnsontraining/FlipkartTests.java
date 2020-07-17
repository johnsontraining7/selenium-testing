package com.johnsontraining;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.johnsontraining.flipkart.pages.BasePage;
import com.johnsontraining.flipkart.pages.LoginPage;

public class FlipkartTests {
	
	private WebDriver driver;
	private LoginPage loginpage;
	
	@BeforeClass
	public void setUp() {
		
		BasePage basePage = new BasePage(driver);
		driver = basePage.getDriver();
		loginpage = new LoginPage(driver);
	}
	
	@Test
	public void closeLoginPopup() {
		
		loginpage.navigateTo("https://www.flipkart.com");
		
		loginpage.closeLoginPage();
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
	
	@DataProvider
	public Object[][] loginData(){
		
		return new Object[][] {{"Invalid username", "Invalid password"}, {"username", "Invalid password"}, {"Invalid username", "password"}};
	}

}
