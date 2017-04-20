package com.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.reporter.utils.Report;

public class ChromeUtils {

	WebDriver getDriver(){
		System.setProperty("webdriver.chrome.driver", Report.PATH +"//drivers//chromedriver.exe");
		return new ChromeDriver();
	}
	
}
