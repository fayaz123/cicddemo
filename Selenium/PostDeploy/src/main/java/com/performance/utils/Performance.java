package com.performance.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Performance {
	
	public Long getFirstByteResponse(WebDriver driver){
		Long response = (Long) ((JavascriptExecutor) driver)
				.executeScript("return performance.timing.responseStart  - performance.timing.requestStart;");
		return response;
	}
	
	public Long getServerResponseTime(WebDriver driver){
	Long response = (Long) ((JavascriptExecutor) driver)
			.executeScript("return performance.timing.loadEventEnd  - performance.timing.requestStart ;");
		return response;
	}
	
	public Long getPageLoadTime(WebDriver driver){
		Long page_load_time = (Long) ((JavascriptExecutor) driver)
				.executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
		return page_load_time;
	}

}
