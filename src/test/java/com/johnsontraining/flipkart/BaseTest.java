package com.johnsontraining.flipkart;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseTest {

	@BeforeSuite
	public void beforeSuite(){
		
		System.out.println("I'm from base beforeSuite");
	}
	
	@BeforeTest
	public void beforeTest(){
		
		System.out.println("I'm from base beforeTest");	
	}
	
	@BeforeClass
	public void beforeClass(){
		
		System.out.println("I'm from base beforeClass");	
	}
	
	@BeforeMethod
	public void beforeMethod(){
		
		System.out.println("I'm from base beforeMethod");	
	}
	
	@AfterSuite
	public void afterSuite(){
		
		System.out.println("I'm from base afterSuite");
	}
	
	@AfterTest
	public void afterTest(){
		
		System.out.println("I'm from base afterTest");	
	}
	
	@AfterClass
	public void afterClass(){
		
		System.out.println("I'm from base afterClass");	
	}
	
	@AfterMethod
	public void afterMethod(){
		
		System.out.println("I'm from base afterMethod");
	}
	
	@BeforeGroups(groups = {"sanity"})
	public void beforeGroups(){
		
		System.out.println("I'm from base beforeGroups");
	}
}
