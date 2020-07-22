package com.johnsontraining.flipkart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage{
	
	@FindBy(xpath="//span[text()='Electronics']")
	private WebElement electronicsOption;
	
	@FindBy(xpath="//li[@class='_1KCOnI _3ZgIXy']/a[@title='Samsung'][contains(@href,'Electronics')]")
	private WebElement samsungButton;
	
	@FindBy(xpath="//li[@class='_1KCOnI _3ZgIXy']/a[@title='Mobiles'][contains(@href,'Electronics')]")
	private WebElement mobilesButton;
	
	@FindBy(xpath="//input[@name='q']")
	private WebElement searchTextBox;

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void selectSamsungOption() {
		
		mouseHoverAndClick(electronicsOption, samsungButton);
	}
	
	public void selectMobilesOption() {
		
		mouseHoverAndClick(electronicsOption, mobilesButton);
	}
	
	public void searchFor(String searchItem) {
		
		enterTextAndEnter(searchTextBox, searchItem);
	}
	
	public void searchForWithCaps(String searchItem) {
		
		enterTextUsingActionsInCaps(searchTextBox, searchItem);
	}

}
