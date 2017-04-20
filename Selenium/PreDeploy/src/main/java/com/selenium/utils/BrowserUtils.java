package com.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtils {

	private static WebDriver driver;
	private static WebDriverWait wait; 
	private static String browser_name; 
	
	void setBrowser(String browser, int timeout) {
		if (browser.equalsIgnoreCase("IE")) {
			driver = new IEUtils().getDriver();
			browser_name = "ie";
			System.out.println("Setting up IE Browser");
		}

		else if (browser.equalsIgnoreCase("CHROME")) {
			driver = new ChromeUtils().getDriver();
			browser_name = "chrome";
			System.out.println("Setting up Chrome Browser");
		}

		else {
			driver = new FirefoxUtils().getDriver();
			browser_name = "firefox";
			System.out.println("Setting up Firefox Browser");
		}

		wait = new ExplicitWaits().initializeWait(driver, timeout);
		System.out.println("Setting up Explicit Wait with timeout " +timeout);
	}
	
	WebDriver getDriver(){
		return driver;
	}
	
	WebDriverWait getWait(){
		return wait;
	}
	
	String getbrowserName(){
		return browser_name;
	}
}
