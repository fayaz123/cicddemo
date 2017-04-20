package com.selenium.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.java.utils.PropertyFile;
import com.performance.utils.Performance;
import com.reporter.utils.Report;

public class SFSpecific extends SeleniumUtils {
	
	void checkPerformance(String data){
		Set<String> keys = PropertyFile.getAllPropertyNames("app");
		for (String key : keys) {
			if(data.contains(key)) {
				data = data.replace(key, PropertyFile.getPropertyStringValue("app", key));
				
			}
		}
		getUrl(data);
		Long first_bype_response = new Performance().getFirstByteResponse(driver);
		Long server_response_time = new Performance().getServerResponseTime(driver);
		Long page_load_time = new Performance().getPageLoadTime(driver);
		DecimalFormat df2 = new DecimalFormat();
		//Float load_time = page_load_time.floatValue() / 1000;
		
		int firstByteResponseTime = PropertyFile.getPropertyIntValue("performance", "firstByteResponseTime");
		int serverResponseTime = PropertyFile.getPropertyIntValue("performance", "serverResponseTime");
		int pageLoadTime = PropertyFile.getPropertyIntValue("performance", "pageLoadTime");
		
		if(first_bype_response > firstByteResponseTime) {
			new Report().log_fail("First Byte Response Time : " +df2.format(first_bype_response) + " ms for URL : " +data);
		} else {
			new Report().log_pass("First Byte Response Time : " +df2.format(first_bype_response) + " ms for URL : " +data);					
		}
		
		if(server_response_time > serverResponseTime) {
			new Report().log_fail("Server Response Time : " +df2.format(server_response_time) + " ms for URL : " +data);
		} else {
			new Report().log_pass("Server Response Time : " +df2.format(server_response_time) + " ms for URL : " +data);					
		}
		
		if(page_load_time > pageLoadTime) {
			new Report().log_fail("Page load time : " +df2.format(page_load_time) + " ms for URL : " +data);
		} else {
			new Report().log_pass("Page load time : " +df2.format(page_load_time) + " ms for URL : " +data);					
		}
	}
	
	void unpackData(ArrayList<By> locator, String data){
		waitForPageLoaded();
		enter(locator.get(0), data);
		waitForElementToDisappear(locator.get(3));
		wait(4);
		WebElement ele = findElement(locator.get(1));
		List<WebElement> row = ele.findElements(By.tagName("tr"));
		boolean flag = false;
		for (int i = 0; i < row.size(); i++) {
			WebElement checkbox_col = row.get(i).findElements(By.tagName("td")).get(0);
			WebElement checkbox_name = row.get(i).findElements(By.tagName("td")).get(1);
			if(checkbox_name.getText().equals(data)) {
				flag = true;
				WebElement e = checkbox_col.findElement(By.xpath(".//*[@id='pagestoimport']"));
				e.click();
				break;
			}
		}
		
		verifyValues(flag, true, data +" not found !!");	
		
		click(locator.get(2));
		String act = getText(locator.get(3));
		if(act == null || act == "" || act.isEmpty()){
			act = getAttributeValue(locator.get(3), "title");
		}
		
		if(act == null || act == "" || act.isEmpty()) {
			fail("verification Failed. Check the locator and try again.");
		}
		
		waitForTexttoBePresent(locator.get(3), act);
		waitForElementToDisappear(locator.get(3));
		wait(2);
		new Report().log_pass("Successfully Unpacked " +data);
		System.out.println("Successfully Unpacked " +data);
	}
	
	void lookup(ArrayList<By> locator, String data){
		waitForPageLoaded();
		
		if(! getAttributeValue(locator.get(0), "value").equals(data)) {
			WebElement e = findElement(locator.get(0)).findElement(By.xpath(".//following::img[1]"));
			click(e);
			
			ArrayList<String> tabs = new ArrayList<String>(getWindowHandles());
			try {
				switchToWindow(tabs.get(1));
				waitForPageLoaded();
				wait(3);
			} catch (IndexOutOfBoundsException e2) {
				fail("New Window is not available to switch");
			}
			
			switchToFrame(locator.get(1));
			enter(locator.get(2), data);
			click(locator.get(3));
			wait(2);
			switchToDefaultContent();
			
			switchToFrame(locator.get(4));
			wait(2);
			click(By.linkText(data));
			switchToWindow(tabs.get(0));
		}
	}
	
	void activateVersion(ArrayList<By> locator, String data){
		WebElement elem = findElement(locator.get(0));
		int size = elem.findElements(By.tagName("tr")).size();
		List<WebElement> rows = elem.findElements(By.tagName("tr"));
		for (int i = 0; i < size; i++) {
			WebElement value = rows.get(i).findElements(By.tagName("td")).get(2);
			if(value.getText().equals(data)){
				value = rows.get(i).findElements(By.tagName("td")).get(0);
				List<WebElement> ccc = value.findElements(By.xpath(".//a"));
				WebElement btn = null;
				for (WebElement element : ccc) {
					if(element.getText().equalsIgnoreCase("Activate") || element.getText().equalsIgnoreCase("DeActivate")){
						btn = element;
						break;
					}
				}
				String deact = btn.getText();
				if(deact.equalsIgnoreCase(("Activate"))){
					btn.click();
					wait(2);
					waitForPageLoaded();
					wait(4);
					new Report().log_pass("Verison Number " +data + " is Activated");
					System.out.println("Verison Number " +data + " is Activated");
					break;
				}
				else if(deact.equalsIgnoreCase(("DeActivate"))){
					new Report().log_pass("Verison Number " +data + " is Already Activated");
					System.out.println("Verison Number " +data + " is Already Activated");
					break;
				}
			}
		}
	}
	
	void deActivateVersion(ArrayList<By> locator, String data){
		WebElement elem = findElement(locator.get(0));
		int size = elem.findElements(By.tagName("tr")).size();
		List<WebElement> rows = elem.findElements(By.tagName("tr"));
		for (int i = 0; i < size; i++) {
			WebElement value = rows.get(i).findElements(By.tagName("td")).get(2);
			if(value.getText().equals(data)){
				value = rows.get(i).findElements(By.tagName("td")).get(0);
				List<WebElement> ccc = value.findElements(By.xpath(".//a"));
				WebElement btn = null;
				for (WebElement element : ccc) {
					if(element.getText().equalsIgnoreCase("Activate") || element.getText().equalsIgnoreCase("DeActivate")){
						btn = element;
						break;
					}
				}
				String deact = btn.getText();
				if(deact.equalsIgnoreCase(("Deactivate"))){
					btn.click();
					wait(2);
					waitForPageLoaded();
					wait(4);
					new Report().log_pass("Verison Number " +data + " is De-Activated");
					System.out.println("Verison Number " +data + " is De-Activated");
					break;
				}
				else if(deact.equalsIgnoreCase(("Activate"))){
					new Report().log_pass("Verison Number " +data + " is Already De-Activated");
					System.out.println("Verison Number " +data + " is Already De-Activated");
					break;
				}
			}
		}
	}
	
	void selectSalesApp(ArrayList<By> locator){
		String app = getText(locator.get(0));
		if(! app.equals("Sales")) {
			click(locator.get(0));
			click(By.linkText("Sales"));
		}
		new Report().log_pass("Sales App is selected");
		System.out.println("Sales App is selected");
	}
	
	void editOrgWideEmail(ArrayList<By> locator, String data){
		
		WebElement elem = findElement(locator.get(0));
		int size = elem.findElements(By.tagName("tr")).size();
		List<WebElement> rows = elem.findElements(By.tagName("tr"));
		boolean found = false;
		for (int i = 0; i < size; i++) {
			WebElement value = rows.get(i).findElements(By.tagName("td")).get(2);
			if(value.getText().equals(data)){
				found = true;
				value = rows.get(i).findElements(By.tagName("td")).get(0);
				List<WebElement> ccc = value.findElements(By.xpath(".//a"));
				boolean flag = false;
				for (WebElement element : ccc) {
					if(element.getText().equalsIgnoreCase("Edit")){
						flag = true;
						element.click();
						wait(2);
						waitForPageLoaded();
						new Report().log_pass("Successfully clicked on Edit Link for : " +data);
						System.out.println("Successfully clicked on Edit Link for : " +data);
						break;
					}
				}
		
		if(flag == false) {		
				new Report().log_pass("Not able to find Edit Link for : " +data);
				System.out.println("Not able to find Edit Link for : " +data);
				}
			}
		}
		if(found == false) {
			Assert.fail("Not able to find the : " +data);
		}
	}
	
	void selectProcess(String data){
		data = data.trim();
		By link = By.xpath("//a[text()='"+data+"']/../a/div/span/span");
		click(link);
		wait(3);
		waitForPageLoaded();
	}
	
	void selectVersion(String data){
		data = data.trim();
		By link = By.xpath("//*[@title='"+data+"']/following::a[1]");
		click(link);
		wait(3);
		waitForPageLoaded();
	}
}

