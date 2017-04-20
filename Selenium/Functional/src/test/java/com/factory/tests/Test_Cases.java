package com.factory.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.reporter.utils.Report;
import com.selenium.utils.Configurations;

public class Test_Cases extends Configurations {
	
	@Test(dataProvider="data")
	public void Execute_Configuration(String column0, String column1){
		new Report().log_test_name(column0);
		getMaster().execute(column0, column1);
	}
	
	@DataProvider(name = "data")
	public String[][] getData() {
		return getExcel("Master.xlsx").getExcelData("Config"); 
	}
}
