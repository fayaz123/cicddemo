package com.excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.reporter.utils.Report;
import com.selenium.utils.Configurations;

public class Excel {
	
	String fName;
	String sName;
	
	public Excel(String fileName){
		fName = fileName;
	}
	
	public String[][] getExcelData(String sName) {
		
        Workbook workbook = initializeWorkbook(fName);
        Sheet sheet = workbook.getSheet(sName);
        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
        String[][] data = null;
        Row row = sheet.getRow(0);
    	data = new String[rowCount][row.getLastCellNum()];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < row.getLastCellNum(); j++) {
            	try {
                	data[i][j] = sheet.getRow(i+1).getCell(j).getStringCellValue();
				} catch (NullPointerException e) {
					data[i][j] = "";
				} catch (IllegalStateException x) {
					double d = sheet.getRow(i+1).getCell(j).getNumericCellValue();
					int r = (int) d;
					String s = Integer.toString(r);
					data[i][j] = s.trim();
				}
            }
        }
		return data;
    }
	
	
	public List<String> getSheetNames(){
		Workbook workbook = initializeWorkbook(fName);
		int sheet_count = workbook.getNumberOfSheets();
		List<String> sheetname = new ArrayList<String>();
		
		for (int i = 0; i < sheet_count; i++) {
			sheetname.add(workbook.getSheetName(i));
		}
		return sheetname;
	}
	
	String getExcelFilePath(String fileName){
		String env = Configurations.ENV;
		return Report.PATH +"\\files\\"+env +"\\"+fileName;
	}
	
	/**
	 * This is just a work around for ANT Build deploy
	 * @param fileName
	 * @return
	 */
	String getExcelFilePath2(String fileName){
		String env = Configurations.ENV;
		return System.getProperty("user.dir") +"\\Configurations\\"+"\\files\\" +env +"\\"+fileName;
	}
	
	FileInputStream initializeExcelFile(String fileName){
		File file =  new File(getExcelFilePath(fileName));
	    try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	Workbook initializeWorkbook(String fileName){
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		
		if(fileExtensionName.equals(".xlsx")){
	    	try {
				return new XSSFWorkbook(initializeExcelFile(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    else if(fileExtensionName.equals(".xls")){
	    	try {
				return new HSSFWorkbook(initializeExcelFile(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		return null;
	}
}

	    
