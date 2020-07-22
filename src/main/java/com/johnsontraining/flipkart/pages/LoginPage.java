package com.johnsontraining.flipkart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage{
	
	@FindBy(xpath = "//button[@class='_2AkmmA _29YdH8']")
	WebElement loginPageCloseButton;
	
	@FindBy(xpath = "//button[@class='_2AkmmA _29YdH8']")
	WebElement userNameTextField;
	
	@FindBy(xpath = "//button[@class='_2AkmmA _29YdH8']")
	WebElement passWordTextField;
	
	@FindBy(xpath = "//button[@class='_2AkmmA _29YdH8']")
	WebElement loginSubmitButton;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void closeLoginPage() {
		
		clickElement(loginPageCloseButton);
	}
	
	public void login() {
		
		login("username", "password");
	}
	
	public void login(String username, String password) {
		
		userNameTextField.sendKeys(username);
		passWordTextField.sendKeys(password);
		loginSubmitButton.click();
	}
}
