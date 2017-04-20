package com.factory.utils;

import com.selenium.utils.Configurations;
import com.selenium.utils.SeleniumUtils;

public class Master extends Configurations {
	
	public void execute(String filename, String execute){
		if(execute.equalsIgnoreCase("yes")) {
			filename = filename + ".xlsx";

			int size = getExcel(filename).getSheetNames().size();
			
			for (int i = 0; i < size; i++) {
				String sheet_name = getExcel(filename).getSheetNames().get(i);	
				if(filename == null || filename == "") {
					new SeleniumUtils().fail("Excel sheet row is empty for one of the row field in Master Excel Sheet");
				}
				
				System.out.println();
				System.out.println("Executing File : " +filename);
				System.out.println("Executing Sheet : " +sheet_name);
				getUtils().execute(getExcel(filename).getExcelData(sheet_name));
			}
			
		} else {
			
			System.out.println(filename +" Configuration is Skipped by User");
			log_pass(filename +" Configuration is Skipped by User");
		}
	}
}
