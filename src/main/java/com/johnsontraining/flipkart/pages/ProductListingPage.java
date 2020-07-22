package com.johnsontraining.flipkart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class ProductListingPage extends BasePage {
	
	@FindBys({
		@FindBy(xpath = "//a[@class='_2cLu-l']")
	})
	private List<WebElement> allProducts;
	
	public ProductListingPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public List<String> getAllProducts() {
		
		List<String> productList = new ArrayList<String>();
		waitForElementsVisible(allProducts);
		for(WebElement element : allProducts) {
			productList.add(element.getText());
		}
		
		return productList;
	}

}
