package com.selenium.utils;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.reporter.utils.Report;

public class FirefoxUtils {
	
	WebDriver getDriver(){
		System.setProperty("webdriver.gecko.driver", Report.PATH +"//drivers//geckodriver.exe");
		return new FirefoxDriver(capabilities());
	}
	
	public DesiredCapabilities capabilities(){
		String PROXY = "ptproxy.persistent.co.in:8080";
		Proxy proxy = new Proxy();
		proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY).setSocksProxy(PROXY);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, proxy);
		//cap.setCapability("marionette", true);
		
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
		profile.setPreference("startup.homepage_welcome_url", "about:blank");
		profile.setPreference("startup.homepage_welcome_url.additional","about:blank");
		profile.setPreference("browser.startup.homepage","about:blank");  
		cap.setCapability(FirefoxDriver.PROFILE, profile);
		return cap;
	}
}
