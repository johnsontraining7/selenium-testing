package com.johnsontraining.random;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class TestngFlow 
{
	
	//Product run (Suite) -> 
	//Regression, Sanity, Smoke(Test) -> 
	//LoginFeature, SignupFeature, etc,.(Class) -> 
	//loginWithValidCreds, loginWithInvalidUsername(Method) 
	@BeforeSuite
	public void beforeSuite(){
		
		System.out.println("I'm from beforeSuite");
	}
	
	@BeforeTest
	public void beforeTest(){
		
		System.out.println("I'm from beforeTest");	
	}
	
	@BeforeClass
	public void beforeClass(){
		
		System.out.println("I'm from beforeClass");	
	}
	
	@BeforeMethod
	public void beforeMethod(){
		
		System.out.println("I'm from beforeMethod");	
	}
	
	@Test(priority = 1, description = "Testcase with valid login credentials", alwaysRun = true)
	public void loginWithValidCreds(){
		
//		LoginFeature.login("user", "password");
		System.out.println("I'm from loginWithValidCreds");
	}
	
	@Test(priority = 2, enabled = false)
	public void loginWithInvalidUsername(){
		
//		LoginFeature.login("incorrect_user", "password");
		System.out.println("I'm from loginWithInvalidUsername");
	}
	
	@Test(priority = 3, groups = {"sanity", "regression"})
	public void loginWithInvalidPassword(){
		
//		LoginFeature.login("user", "incorrect_password");
		System.out.println("I'm from loginWithInvalidPassword");
	}
	
	@Test(dependsOnMethods = {"loginWithInvalidPassword"})
	public void loginWithInvalidPassword2(){
		
//		LoginFeature.login("user", "incorrect_password");
		System.out.println("I'm from loginWithInvalidPassword");
	}
	
	@AfterSuite
	public void afterSuite(){
		
		System.out.println("I'm from afterSuite");
	}
	
	@AfterTest
	public void afterTest(){
		
		System.out.println("I'm from afterTest");	
	}
	
	@AfterClass
	public void afterClass(){
		
		System.out.println("I'm from afterClass");	
	}
	
	@AfterMethod
	public void afterMethod(){
		
		System.out.println("I'm from afterMethod");
	}
	
	@BeforeGroups(groups = {"sanity"})
	public void beforeGroups(){
		
		System.out.println("I'm from beforeGroups");
	}
}
