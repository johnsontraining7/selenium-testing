package com.johnsontraining.random;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class XpathExpressions {

	@Test
	public void test1() {
		
    	//Setting up the chromedriver for this platform
        WebDriverManager.chromedriver().setup(); //.m2/chromedriver
        
        //Launching the chrome browser
        WebDriver driver = new ChromeDriver();
        
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        driver.manage().window().maximize();
        
        Dimension dimension = new Dimension(800, 1200);
        
        driver.manage().window().setSize(dimension);
        System.out.println("Window height: " + dimension.getHeight());
        System.out.println("Window width: " + dimension.getWidth());
        
        //Navigate to google.com
        driver.get("https://www.google.com");
        
        driver.findElement(By.xpath("//*[@jsname='vdLsw']/following-sibling::input")).click();
        driver.findElement(By.xpath("//*[@jsname='gLFyf']/child::input")).sendKeys("Selenium");
        driver.findElement(By.xpath("//input[@name='q'][@title='Search']")).sendKeys(Keys.ENTER);
        
        List<WebElement> allSearchResults = driver.findElements(By.xpath("//div[@class='g']//h3"));
        
        for(int i=0; i<allSearchResults.size();i++) {
        	System.out.println("Search result " + i +" is: " + allSearchResults.get(i).getText());
        }
        
        driver.close();
	}
}
