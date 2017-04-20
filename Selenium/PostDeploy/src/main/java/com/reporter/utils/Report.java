package com.reporter.utils;

import java.io.File;
import java.net.URISyntaxException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.utils.ChromeUtils;
import com.selenium.utils.ScreenshotListener;

public class Report {
	public static String PATH;
	//String extentReportFile = PATH +"report"+ "\\report.html";

	static ExtentReports extent;
	static ExtentTest log;
	
	@BeforeSuite
	public ExtentReports start_report(){
		PATH = path();
		extent = new ExtentReports(PATH +"\\report"+ "\\report.html", true);
		return extent;
	}
	
	public ExtentTest log_test_name(String test_name) {
		log = extent.startTest(test_name, "");
		return log;
	}
	
	public void log_pass(String msg) {
		log.log(LogStatus.PASS, msg);
	}
	
	public void log_skip(String msg) {
		log.log(LogStatus.SKIP, msg);
	}
	
	public void log_fail(String msg) {
		log.log(LogStatus.FAIL, msg);
	}
	
	@AfterSuite
	public void close_report(){
		extent.endTest(log);
		extent.flush();
	}
	
	@AfterMethod
	public void log_fail(ITestResult result){
		if(! result.isSuccess()) {
			log.log(LogStatus.FAIL, result.getThrowable().getMessage());
			log.log(LogStatus.FAIL, log.addScreenCapture(ScreenshotListener.destFile.getAbsolutePath()));
		}
	}
	
	private String path(){
		File file = null;
		String path = null;
		try {
			file = new File(ChromeUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			path = file.getPath();
			path = path.replace("\\bin", "");
			path.trim();
			System.out.println(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return path;
	}
}
