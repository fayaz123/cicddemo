package com.selenium.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.reporter.utils.Report;

public class SeleniumUtils extends BrowserUtils {
	
	WebDriver driver = getDriver(); 
	WebDriverWait wait = getWait();
	
	// C :-

	void click(By locator) {
		waitForPageLoaded();
		waitForElementtoBeVisible(locator);
		waitForElementtoBeClickable(locator);
		
		if(! getbrowserName().equals("ie")) {
			try {
				driver.findElement(locator).click();
			} catch (WebDriverException e) {
				clickUsingJavaScript(locator);
			}
		}
		else {
			clickUsingJavaScript(locator);
		}
	}
	
	void click(WebElement element) {
		waitForElementtoBeVisible(element);
		waitForElementtoBeClickable(element);
		
		if(! getbrowserName().equals("ie")) {
			try {
				element.click();
			} catch (WebDriverException e) {
				clickUsingJavaScript(element);
			}
		}
		else {
			clickUsingJavaScript(element);
		}
	}
	
	private void clickUsingJavaScript(By locator) {
		waitForElementtoBeVisible(locator);
		waitForElementtoBeClickable(locator);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", findElement(locator));
	}
	
	private void clickUsingJavaScript(WebElement element) {
		waitForElementtoBeVisible(element);
		waitForElementtoBeClickable(element);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
	}
	
	private void closeCurrentTab() {
		driver.close();
		/*if(getWindowHandles().size() > 1) {
			driver.close();
		}*/
	}

	void closeAllinstances() {
		wait(3);
		driver.quit();
		driver = null;
		wait = null;
	}

	// E :-

	void enter(By locator, String txt) {
		waitForElementtoBeVisible(locator);
		if (!isElementEnable(locator)) {
			wait(5);
		}
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(txt);
	}

	// F :-
	
	WebElement findElement(By locator) {
		waitForElementtoBePresent(locator);
		waitForElementtoBeVisible(locator);
		return driver.findElement(locator);
	}
	
	// G :-

	void getUrl(String url) {
		driver.manage().window().maximize();
		driver.get(url);
		System.out.println("User have entered URL successfully: " +url);
	}

	String getText(By locator) {
		waitForElementtoBePresent(locator);
		waitForElementtoBeVisible(locator);
		return driver.findElement(locator).getText();
	}
	
	String getAttributeValue(By locator, String attribute) {
		waitForElementtoBeVisible(locator);
		waitForElementtoBePresent(locator);
		return driver.findElement(locator).getAttribute(attribute);
	}

	/**
	 * This method will be used to get all the windows.
	 * 
	 * @return
	 */
	Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	/**
	 * This method will used to switch to a window using window handle
	 * @param windowHandle
	 */
	void switchToWindow(String windowHandle) {
		driver.switchTo().window(windowHandle);
	}

	// I :-

	private boolean isElementEnable(By locator) {
		return driver.findElement(locator).isEnabled();
	}

	// M :-

	/**
	 * This method will used for mouse hover elements to hover the element
	 * @param locator
	 */
	private void moveToElement(By locator) {
		Actions actions = new Actions(driver);
		//waitForElementtoBeVisible(locator);
		WebElement move = driver.findElement(locator);
		actions.moveToElement(move).build().perform();
	}
	
	/**
	 * This method will used for mouse hover elements to hover the element
	 * @param locator
	 */
	private void moveToElement(WebElement element) {
		Actions actions = new Actions(driver);
		//waitForElementtoBeVisible(locator);
		actions.moveToElement(element).build().perform();
	}

	// S :-

	/**
	 * This method will select option from dropdown by using visible text
	 * @param locator
	 * @param value
	 */
	private void selectByVisibleText(By locator, String value) {
		waitForElementtoBeVisible(locator);
		WebElement dropdown = driver.findElement(locator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}
	
	/**
	 * This method will select option from dropdown by using visible text
	 * @param locator
	 * @param value
	 */
	private void deSelectByVisibleText(By locator, String value) {
		waitForElementtoBeVisible(locator);
		WebElement dropdown = driver.findElement(locator);
		Select select = new Select(dropdown);
		select.deselectByVisibleText(value);
	}
	
	/**
	 * This method will select the check box
	 * @param locator
	 */
	private void selectCheckBox(By locator) {
		waitForElementtoBeVisible(locator);
		if (!driver.findElement(locator).isSelected()) {
			driver.findElement(locator).click();
		}
	}

	/**
	 * This method will de-select the check box
	 * @param locator
	 */
	private void deselectCheckBox(By locator) {
		waitForElementtoBeVisible(locator);
		if (driver.findElement(locator).isSelected()) {
			driver.findElement(locator).click();
		}
	}

	/**
	 * This method will switch to frame using frame locator
	 * @param frameId
	 */
	void switchToFrame(By locator) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	

	/**
	 * This method will used to switch back to the default page
	 */
	void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	/**
	 * This method will switch to the alert and accept it
	 */
	private void switchToAlertAndAccept() {
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
	}

	/**
	 * This method will switch to the alert and dismiss it
	 */
	private void switchToAlertAndDismiss() {
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().dismiss();
	}

	private void verifyValues(String actual, String expected, String msg) {
		 Assert.assertEquals(actual, expected, msg);
	}
	
	void verifyValues(boolean actual, boolean expected, String msg) {
		 Assert.assertEquals(actual, expected, msg);
	}

	public void fail(String message){
		Assert.fail(message);
	}

	// W :-
	private void waitForElementtoBeClickable(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));

		} catch (TimeoutException e) {
			Assert.fail("Element is not clickable : " + locator);
		}
	}
	
	private void waitForElementtoBeClickable(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));

		} catch (TimeoutException e) {
			Assert.fail("Element is not clickable : " + element);
		}
	}

	private void waitForElementtoBeVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			if(! isWebElementVisible(driver.findElement(locator))) {
				try{
					moveToElement(locator);
				} catch(Exception e){}
			}
		} catch (TimeoutException e) {
			Assert.fail("Element is not visible : " + locator);
		}
	}
	
	private void waitForElementtoBeVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if(! isWebElementVisible(element)) {
				try{
					moveToElement(element);
				} catch(Exception e){}
			}
		} catch (TimeoutException e) {
			Assert.fail("Element is not visible : " + element);
		}
	}

	private void waitForElementtoBePresent(By locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		} catch (TimeoutException e) {
			Assert.fail("Element is not present : " + locator);
		}
	}
	
	void waitForTexttoBePresent(By locator, String text) {
		try {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));

		} catch (TimeoutException e) {
			Assert.fail("Text is not present in the element : " + locator + " and text " +text);
		}
	}
	
	void waitForElementToDisappear(By locator) {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			wait(1);
		} catch (TimeoutException e) {
		}
	}
	
	public void waitForPageLoaded() {
		wait(1);
        /*ExpectedCondition<Boolean> page = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver input) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
        };
        wait.until(page);*/
	}
	

	void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			System.out.println("Failed to wait for :" + seconds + " seconds");
		}
	}

	private boolean isWebElementVisible(WebElement w) {
	    Dimension weD = w.getSize();
	    Point weP = w.getLocation();
	    Dimension d = driver.manage().window().getSize();

	    int x = d.getWidth();
	    int y = d.getHeight();
	    int x2 = weD.getWidth() + weP.getX();
	    int y2 = weD.getHeight() + weP.getY();

	    return x2 <= x && y2 <= y;
	  }
	
	
	public void verifyTableValue(By locator, int row, int col, String expectedTxt, String msg){
		WebElement ele = driver.findElement(locator);
		List<WebElement> rows = ele.findElements(By.tagName("tr"));
		
		if(rows.size() > row) {
			List<WebElement> cols = rows.get(row).findElements(By.tagName("tr"));
			String output = cols.get(col).getText();
			verifyValues(output, expectedTxt, msg);
		}
	}
	
	public enum Perform {
		List, DeSelect, Checkbox, Radio, Button, Link,
		Textbox, Verify, URL, Performance, 
		SwitchToFrame, SwitchToDefault, 
		AcceptAlert, DismissAlert, 
		SwitchToNewWindow, SwitchToDefaltWindow,
		MouseHover, CloseTab, OpenTab, WaitForElementVisible, 
		Unpack, Lookup, ActivateVersion, DeActivateVersion, SelectSalesApp, EditOrgWideEmail,
		SelectProcess, SelectVersion
	}
	
	protected void performActions(String type, ArrayList<By> locator, String data, String field){
		Perform perform = Perform.valueOf(type.trim());
		
		switch (perform) {
			case Button:
				click(locator.get(0));
				new Report().log_pass("Successfully clicked on " + field + " Button");
				System.out.println("Successfully clicked on " + field + " Button");
				break;
				
			case Checkbox:
				if(data.equalsIgnoreCase("Checked")){
					selectCheckBox(locator.get(0));
					new Report().log_pass("Selected checkbox for " +field);
					System.out.println("Selected checkbox for " +field);
				} else {
					deselectCheckBox(locator.get(0));
					new Report().log_pass("Un Selected checkbox for " +field);
					System.out.println("Un Selected checkbox for " +field);
				}
				break;
				
			case Link:
				click(locator.get(0));
				new Report().log_pass("Successfully clicked on " + field + " Link");
				System.out.println("Successfully clicked on " + field + " Link");
				break;
				
			case List:
				waitForPageLoaded();
				selectByVisibleText(locator.get(0), data);
				new Report().log_pass("Selected " +data + " from " + field + " dropdown");
				System.out.println("Selected " +data + " from " + field + " dropdown");
				break;
				
			case DeSelect:
				waitForPageLoaded();
				deSelectByVisibleText(locator.get(0), data);
				new Report().log_pass("De Selected " +data + " from " + field + " dropdown");
				System.out.println("DE Selected " +data + " from " + field + " dropdown");
				break;
				
			case Radio:
				waitForPageLoaded();
				if(data.equalsIgnoreCase("yes")) {
					boolean selected = findElement(locator.get(0)).isSelected();
					if(!selected) {
						click(locator.get(0));
						new Report().log_pass("Successfully clicked on " + field + " Radio Button");
						System.out.println("Successfully clicked on " + field + " Radio Button");
					}
				}
				break;
				
			case Textbox:
				waitForPageLoaded();
				
				if(data.equals("${username}")) {
					data = Configurations.USERNAME;
				} 
				else if(data.equals("${password}")) {
					data = Configurations.PASSWORD;
				}
				enter(locator.get(0), data);
				if(field.equalsIgnoreCase("password")){
					new Report().log_pass("Entered Password Successfully");
					System.out.println("Entered Password Successfully");
					break;
				}
				new Report().log_pass("Entered " +data + " in " +field + " field");
				System.out.println("Entered " +data + " in " +field);
				break;
				
			case Verify:
				waitForPageLoaded();
				String actual = getText(locator.get(0));
				if(actual == null || actual == "" || actual.isEmpty()){
					actual = getAttributeValue(locator.get(0), "title");
				}
				verifyValues(actual, data, "Verification Failed on Field : " +field);
				new Report().log_pass("Successfully Validated " +field);
				System.out.println("Successfully Validated " +field);
				break;
				
			case URL:
				waitForPageLoaded();
				if(data.equals("${url}")) {
					data = Configurations.URL;
				}
				
				getUrl(data);
				waitForPageLoaded();
				new Report().log_pass("User have entered URL successfully");
				break;
				
			case MouseHover :
				wait(2);
				moveToElement(locator.get(0));
				new Report().log_pass("Successfully moved to field " +field);
				System.out.println("Successfully moved to field " +field);
				break;
				
			case SwitchToFrame :
				waitForPageLoaded();
				switchToFrame(locator.get(0));
				new Report().log_pass("Successfully switch to frame " +field);
				System.out.println("Successfully switch to frame " +field);
				break;
				
			case SwitchToDefault :
				waitForPageLoaded();
				switchToDefaultContent();
				new Report().log_pass("Switch to default main frame");
				System.out.println("Switch to default main frame");
				break;
				
			case AcceptAlert :
				waitForPageLoaded();
				switchToAlertAndAccept();
				new Report().log_pass("Accepted Alert " +field);
				System.out.println("Accepted Alert " +field);
				break;
				
			case DismissAlert :
				waitForPageLoaded();
				switchToAlertAndDismiss();
				new Report().log_pass("Dismissed Alert " +field);
				System.out.println("Dismissed Alert " +field);
				break;
				
			case SwitchToNewWindow :
				waitForPageLoaded();
				ArrayList<String> tab_new = new ArrayList<String>(getWindowHandles());
				switchToWindow(tab_new.get(1));
				new Report().log_pass("Successfully switched to window " +field);
				System.out.println("Successfully switched to window " +field);
				break;
				
			case SwitchToDefaltWindow :
				waitForPageLoaded();
				ArrayList<String> tab_old = new ArrayList<String>(getWindowHandles());
				switchToWindow(tab_old.get(0));
				new Report().log_pass("Successfully switched to default window " +field);
				System.out.println("Successfully switched to default window " +field);
				break;
				
			case CloseTab :
				closeCurrentTab();
				new Report().log_pass("Successfully closed tab  " +field);
				System.out.println("Successfully closed tab  " +field);
				break;
				
			case OpenTab :
				String browser = Configurations.BROWSER;
				int timeout = Configurations.TIMEOUT;
				setBrowser(browser, timeout);
				driver = super.getDriver();
				wait = super.getWait();
				break;
				
			case WaitForElementVisible :
				waitForElementtoBeVisible(locator.get(0));
				new Report().log_pass("Wait for Element To be Visible " +field);
				System.out.println("Wait for Element To be Visible " +field);
				break;
				
			case Performance :
				new SFSpecific().checkPerformance(data);
				break;
				
			case Unpack :
				new SFSpecific().unpackData(locator, data);
				break;
				
			case Lookup :
				if(data.isEmpty() || data==null || data.equals("")) {
					break;
				}
				new SFSpecific().lookup(locator, data);
				break;
				
			case DeActivateVersion :
				new SFSpecific().deActivateVersion(locator, data);
				break;
				
			case ActivateVersion :
				new SFSpecific().activateVersion(locator, data);
				break;
				
			case SelectSalesApp :
				new SFSpecific().selectSalesApp(locator);
				break;
				
			case EditOrgWideEmail :
				new SFSpecific().editOrgWideEmail(locator, data);
				break;
			case SelectProcess:
				new SFSpecific().selectProcess(data);
				break;
				
			case SelectVersion:
				new SFSpecific().selectVersion(data);
				break;
			
		}
	}
	
	private enum Locators {
		xpath, link, id, className
	}
	
	protected ArrayList<By> toLocator(String locator_type, ArrayList<String> list){
		
		ArrayList<By> l = new ArrayList<By>();
		
		if(list.size() > 1 ) {
			for (int i = 0; i < list.size(); i++) {
				String locator_value = list.get(i); 
				By loc = toLocator(locator_type, locator_value);
				l.add(i, loc);
			}
			return l;
		}
		else {
			String locator_value = list.get(0); 
			By loc = toLocator(locator_type, locator_value);
			l.add(0, loc);
			return l;
		}
	}
	
	protected By toLocator(String locator_type, String locator_value){
		if(locator_type.trim().isEmpty()) {
			return null;
		}
		Locators loc = Locators.valueOf(locator_type.trim());
		switch (loc) {
			case xpath :
				return By.xpath(locator_value);
			case link :
				return By.linkText(locator_value);
			case id :
				return By.id(locator_value);
		case className:
			return By.className(locator_value);
		}
		return null;
	}
}
