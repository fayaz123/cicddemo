package com.factory.utils;

import java.util.ArrayList;

import com.excel.utils.Excel;
import com.selenium.utils.SeleniumUtils;

public class Utils extends SeleniumUtils{
	
	public void perform(String type, String data, String locator_type, String locator_value, String field){
		if(type == null || type == "") {
			fail("Excel sheet type is empty for one of the field");
		}
		
		ArrayList<String> list = new ArrayList<String>(); 
		String[] s = locator_value.split(";");
		int size = s.length;
		
		if (size > 1)
			for (int i = 0; i < size; i++) {
				list.add(s[i].trim());
			}
		else
			list.add(s[0].trim());
		
		performActions(type, toLocator(locator_type, list), data, field);
	}
	
	public void execute(String[][] excel_data){
		for (String[] col : excel_data) {
			String field = col[2];
			String type = col[3];
			String data = col[4]; 
			String locator_type = col[5]; 
			String locator_value = col[6];
			
			if(type.equalsIgnoreCase("include")) {
				include(type, data);
			} else {
				perform(type, data, locator_type, locator_value, field);
			}
		}
	}
	
	public void include(String type, String data){
			int size = new Excel(data).getSheetNames().size();
			for (int i = 0; i < size; i++) {
				String sheet_name = new Excel(data).getSheetNames().get(i);	
				for (String[] col : new Excel(data).getExcelData(sheet_name)) {
					String field = col[2];
					type = col[3];
					data = col[4]; 
					String locator_type = col[5]; 
					String locator_value = col[6];
					perform(type, data, locator_type, locator_value, field);
			}
		}
	}
}
