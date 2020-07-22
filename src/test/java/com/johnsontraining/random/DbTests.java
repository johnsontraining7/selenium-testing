package com.johnsontraining.random;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DbTests {

	@Test
	public void readFromDb() throws ClassNotFoundException, SQLException {
		
		Map<Integer, Map<String, Object>> allUsersMap = new LinkedHashMap<Integer, Map<String, Object>>();
		String dbUrl = "jdbc:mysql://127.0.0.1:3036/training";
		String dbUserName = "root";
		String dbPassWord = "pass1234";
		
		// Step-1 : Choose the right driver for the db you've chosen and initialize the driver.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// Step-2 : Using the driver manager establish a db-connection.
		Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassWord);
		
		// Step-3 : Creating a statement for the db-connection that was established.
		Statement statement = connection.createStatement();
		
		// Step-4 : Using the statement execute the queries on the db.
		ResultSet resultSet = statement.executeQuery("SELECT * FROM training.user;");
		
		// Step-5 : Process the output.
		while (resultSet.next()) {
			Map<String, Object> currentUserMap = new HashMap<String, Object>();
			currentUserMap.put("name", resultSet.getString("name"));
			currentUserMap.put("email", resultSet.getString("email"));
			currentUserMap.put("age", resultSet.getInt("age"));
			currentUserMap.put("hobbies", resultSet.getString("hobbies"));
			
			allUsersMap.put(resultSet.getInt("id"), currentUserMap);
		}
		
		for(Map.Entry<Integer, Map<String, Object>> entry : allUsersMap.entrySet()) {
			System.out.println(String.format("Key: %s, value: %s", entry.getKey(), entry.getValue()));
		}
	}
	
	@Test
	public void writeToDb() throws ClassNotFoundException, SQLException {
		
		Map<Integer, Map<String, Object>> allUsersMap = new LinkedHashMap<Integer, Map<String, Object>>();
		String dbUrl = "jdbc:mysql://127.0.0.1:3036/training";
		String dbUserName = "root";
		String dbPassWord = "pass1234";
		
		// Step-1 : Choose the right driver for the db you've chosen and initialize the driver.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// Step-2 : Using the driver manager establish a db-connection.
		Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassWord);
		
		// Step-3 : Creating a statement for the db-connection that was established.
		Statement statement = connection.createStatement();
		
		// Step-4 : Using the statement execute the queries on the db.
		int code = statement.executeUpdate("insert into user(name, email, age, hobbies) values('user4', 'user4@xyz.com', '24', 'cricket, gardening');");
		
		// Step-5 : Process the output.
		boolean status = code == 1 ? true : false;
		Assert.assertTrue(status, "Executing the db update failed..!");
	}
}
