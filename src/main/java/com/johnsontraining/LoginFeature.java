package com.johnsontraining;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class LoginFeature 
{
	private static WebDriver driver;
	
    public static void main( String[] args )
    {
    	//Setting up the chromedriver for this platform
        WebDriverManager.chromedriver().setup(); //.m2/chromedriver
        
        //Launching the chrome browser
        driver = new ChromeDriver();
        
        //Navigate to google.com
        driver.get("https://www.google.com");
        
        driver.findElement(By.xpath("//input[@name='q'][@title='Search']")).click();
        driver.findElement(By.xpath("//input[@name='q'][@title='Search']")).sendKeys("Selenium");
        driver.findElement(By.xpath("//input[@name='q'][@title='Search']")).sendKeys(Keys.ENTER);
        
        driver.close();
    }
    
    public static void login(String username, String password) {
    	
//    	driver.findElement(By.name("username")).sendKeys("username");
//    	driver.findElement(By.name("password")).sendKeys("password");
    	if(username.equalsIgnoreCase("user")) {
    		System.out.println("Test passed");
    	} else if(username.equalsIgnoreCase("incorrect_user")) {
    		throw new WebDriverException("Something bad happened and my testcase failed");
    	}
    }
}
