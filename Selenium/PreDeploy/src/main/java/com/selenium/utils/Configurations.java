package com.selenium.utils;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.excel.utils.Excel;
import com.factory.utils.Master;
import com.factory.utils.Utils;
import com.reporter.utils.Report;

public class Configurations extends Report{
	
	public static String ENV;
	public static String BROWSER;
	public static int TIMEOUT;
	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	
	@BeforeTest(alwaysRun = true)
	@Parameters({"browser", "timeout", "org", "url", "username" , "password"})
	protected void initialize(String browser, int timeout, String org, String url, String username, String password) {
		ENV = org;
		BROWSER = browser;
		TIMEOUT = timeout;
		URL = url;
		USERNAME = username;
		PASSWORD = password;
		new BrowserUtils().setBrowser(browser, timeout);
	}
	
	@AfterTest(alwaysRun = true)
	protected void quit(){
		new SeleniumUtils().closeAllinstances();
		System.out.println("Closing Browser");
	}
	
	public Excel getExcel(String fileName){
		return new Excel(fileName);
	}
	
	public Utils getUtils(){
		return new Utils();
	}
	
	public Master getMaster(){
		return new Master();
	}
	
}
