package com.johnsontraining;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderUsage {

	@Test(priority = 1, description = "Testcase with valid login credentials", alwaysRun = true, dataProvider = "loginDataProvider")
	public void loginWith(String username, String password){
		
		System.out.println("Username: " + username + ", Password: " + password);
	}
	
	@DataProvider
	public Object[][] loginDataProvider() {
		
		return new Object[][] {
			{"user", 			"password"}, 
			{"invalid_user", 	"password"}, 
			{"user", 			"invalid_password"}};
	}
}
