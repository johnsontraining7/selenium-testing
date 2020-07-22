package com.johnsontraining.random;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class FileReadTests {
	
	//BufferedReader to read file
	@Test
	public void readUsingBr() throws IOException {
		
		FileReader fr = new FileReader(new File("src/test/resources/testdata.txt"));
		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		while((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}
	
	@Test
	public void readUsingScanner() throws IOException {
		
		Scanner scanner = new Scanner(new File("src/test/resources/testdata.txt"));

		while(scanner.hasNextLine()) {
			System.out.println(scanner.nextLine());
		}
		scanner.close();
	}
	
	@Test
	public void readUsingString() throws IOException {
		
		String filePath = "src/test/resources/testdata.txt";

		String allLines = new String(Files.readAllBytes(Paths.get(filePath)));
		
		System.out.println(allLines);
	}
	
	@Test
	public void readPropertiesFile() throws IOException {
		
		Properties properties = new Properties();
		FileReader reader = new FileReader(new File("src/test/resources/config.properties"));
//		FileInputStream fis = new FileInputStream(new File("src/test/resources/config.properties"));
		
		properties.load(reader);
		
		System.out.println(properties.getProperty("driver"));
		System.out.println(properties.getProperty("baseUrl"));
		System.out.println(properties.getProperty("testdatavalueset1").split(","));
	}
	
	@Test
	public void writePropertiesFile() throws IOException {
		
		Properties properties = new Properties();
		
		FileInputStream fis = new FileInputStream(new File("src/test/resources/config.properties"));
		properties.load(fis);
		fis.close();
		
//		FileWriter
		FileOutputStream fos = new FileOutputStream(new File("src/test/resources/config.properties"));
		
		properties.setProperty("driver", "chrome");
		properties.store(fos, "My updated values");
		
		System.out.println(properties.getProperty("driver"));
	}
	
	@Test
	public void readExcelFile() throws IOException {
		
		FileInputStream fis = new FileInputStream(new File("src/test/resources/testdata.xlsx"));
		
		Workbook workbook = new XSSFWorkbook(fis);// XSSF -> Xml-Spread-Sheet-Format
//		Workbook workbook2 = new HSSFWorkbook(fis);// HSSF -> Horrible-Spread-Sheet-Format
		
		Sheet sheet1 = workbook.getSheet("Sheet1");
//		Sheet sheet1 = workbook.getSheetAt(0);
		
		Row headerRow = sheet1.getRow(0);
		
		for(int i = 1 ; i < sheet1.getLastRowNum(); i++) {
			Row currentRow = sheet1.getRow(i);
			for(int j = 0; j < currentRow.getLastCellNum(); j++) {
				if(currentRow.getCell(j).getCellType().name().equals(CellType.STRING.name())) {
					System.out.println(currentRow.getCell(j).getStringCellValue());
				} else if (currentRow.getCell(j).getCellType().name().equals(CellType.NUMERIC.name())) {
					System.out.println(currentRow.getCell(j).getNumericCellValue());
				}
			}
		}
		
		fis.close();
	}
	
	@Test
	public void readExcelFileUsingMap() throws IOException {
		
		Map<String, Map<String, Object>> allUsersMap = new LinkedHashMap<String, Map<String, Object>>();
		
		FileInputStream fis = new FileInputStream(new File("src/test/resources/testdata.xlsx"));
		
		Workbook workbook = new XSSFWorkbook(fis);// XSSF -> Xml-Spread-Sheet-Format
//		Workbook workbook2 = new HSSFWorkbook(fis);// HSSF -> Horrible-Spread-Sheet-Format
		
//		Sheet sheet1 = workbook.getSheet("Sheet1");
		Sheet sheet1 = workbook.getSheetAt(0);
		
		//header-row
		Row headerRow = sheet1.getRow(0);
		
		for(int i = 1 ; i <= sheet1.getLastRowNum(); i++) { // Iterates thru every row
			Map<String, Object> currentUserMap = new HashMap<String, Object>();
			
			Row currentRow = sheet1.getRow(i);				// stores the current row
			for(int j = 1; j < currentRow.getLastCellNum(); j++) { // iterate thru every cell in the current row
				
				if(currentRow.getCell(j).getCellType().name().equals(CellType.STRING.name())) {
					currentUserMap.put(headerRow.getCell(j).getStringCellValue(), currentRow.getCell(j).getStringCellValue());
//					System.out.println(currentRow.getCell(j).getStringCellValue());
				} else if (currentRow.getCell(j).getCellType().name().equals(CellType.NUMERIC.name())) {
					currentUserMap.put(headerRow.getCell(j).getStringCellValue(), currentRow.getCell(j).getNumericCellValue());
//					System.out.println(currentRow.getCell(j).getNumericCellValue());
				}
			}
			allUsersMap.put(currentRow.getCell(0).getStringCellValue(), currentUserMap);
		}
		
		for(Map.Entry<String, Map<String, Object>> entry : allUsersMap.entrySet()) {
			System.out.println(String.format("Key: %s, value: %s", entry.getKey(), entry.getValue()));
//			System.out.println("Key: " + entry.getKey() + ", value: " + entry.getValue());
		}
		
		fis.close();
	}
	
	public Map<String, Object> findSpecificUser(String username, Map<String, Map<String, Object>> allUsersMap){
		
		return allUsersMap.get(username);
	}
	
	@Test
	public void writeToExcel() throws IOException {
		
		List<String> usernames = new ArrayList<String>();
		usernames.add("user1");
		usernames.add("user2");
		usernames.add("user3");
		usernames.add("user4");
		usernames.add("user5");
		
		List<Long> mobile = new ArrayList<Long>();
		mobile.add(9876543210L);
		mobile.add(9876543211L);
		mobile.add(9876543212L);
		mobile.add(9876543213L);
		mobile.add(9876543214L);
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("userDetails");// workbook.getSheetAt(0);
		Row row = sheet1.createRow(0); // sheet1.getRow(i);
		row.createCell(0).setCellValue("User");
		row.createCell(1).setCellValue("Mobile");
		for(int i = 1; i <= usernames.size() ; i++) {
			Row currentRow = sheet1.createRow(i);
			currentRow.createCell(0).setCellValue(usernames.get(i-1));
			currentRow.createCell(1).setCellValue(mobile.get(i-1));
		}
		
		FileOutputStream fos = new FileOutputStream(new File("src/test/resources/generated-user-data.xlsx"));
		workbook.write(fos);
		
		workbook.close();
		fos.close();
	}
}
