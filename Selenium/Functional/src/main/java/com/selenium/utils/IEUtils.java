package com.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.reporter.utils.Report;

public class IEUtils {
	
	WebDriver getDriver(){
		System.setProperty("webdriver.ie.driver",Report.PATH +"//drivers//IEDriverServer.exe");
		return new InternetExplorerDriver();
	}
	

}
