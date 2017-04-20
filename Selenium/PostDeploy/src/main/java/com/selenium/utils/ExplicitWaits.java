package com.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExplicitWaits {

	public WebDriverWait initializeWait(WebDriver driver, int timeout){
		return new WebDriverWait(driver, timeout);
	}
}
