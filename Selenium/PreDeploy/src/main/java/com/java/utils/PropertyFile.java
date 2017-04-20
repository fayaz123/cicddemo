package com.java.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import com.reporter.utils.Report;

public class PropertyFile {
	
	private static Properties properties = new Properties();
	private static FileInputStream input = null;
	
	private static Properties readPropertyFile(String fileName){
		try {
			String file_location = Report.PATH +"\\" +fileName +".properties";
			input = new FileInputStream(file_location);
			properties.load(input);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return properties;
	}
	
	
	public static String getPropertyStringValue(String fileName, String key){
		return readPropertyFile(fileName).getProperty(key);
	}
	
	public static int getPropertyIntValue(String fileName, String key){
		return Integer.parseInt(readPropertyFile(fileName).getProperty(key));
	}
	
	public static Set<String> getAllPropertyNames(String fileName){
		return readPropertyFile(fileName).stringPropertyNames();
	}
}
