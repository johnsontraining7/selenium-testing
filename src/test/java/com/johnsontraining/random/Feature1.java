package com.johnsontraining.random;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.johnsontraining.flipkart.BaseTest;

public class Feature1 extends BaseTest{

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
		System.out.println("I'm from loginWithInvalidPassword2");
	}
}
