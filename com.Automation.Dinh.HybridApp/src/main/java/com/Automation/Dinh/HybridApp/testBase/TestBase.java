package com.Automation.Dinh.HybridApp.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class TestBase {
	public WebDriver driver;
	public Properties OR;
	public File file;
	public FileInputStream inputStr;
	public void getBrowser(String browser) {
		System.out.println(System.getProperty("os.name"));
		if(System.getProperty("os.name").contains("Window")) {
			if(browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}
			else if(browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver= new ChromeDriver();
			}
		}
		
		
		if(System.getProperty("os.name").contains("Mac")) {
			if(browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir")+"/drivers/geckodriver");
				driver = new FirefoxDriver();
			}
			else if(browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver");
				driver= new ChromeDriver();
			}
		}
	}
	public void loadPropertiesFile() throws IOException {
		OR = new Properties();
		file= new File(System.getProperty("user.dir")+"/src/main/java/com/Automation/Dinh/HybridApp/config/config.properties");
		inputStr = new FileInputStream(file);
		OR.load(inputStr);
		
		file= new File(System.getProperty("user.dir")+"/src/main/java/com/Automation/Dinh/HybridApp/config/or.properties");
		inputStr = new FileInputStream(file);
		OR.load(inputStr);
	}
	
	public void getScreenShot(String imageName) throws IOException {
		if(imageName.equals("")) {
			imageName="_blank";
		}
		File fileScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imageLocation = System.getProperty("user.dir")+"/src/main/java/com/Automation/Dinh/HybridApp/screenshot";
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = imageLocation+ imageName +"_"+formater.format(calender.getTime())+".png"; 
		File destFile = new File(actualImageName);
		FileUtils.copyDirectory(fileScreenshot, destFile);
		
	}
	public void getPropertiesData() {
		
	}
	
	public static void main(String[]args) throws IOException {
		TestBase test = new TestBase();
		test.loadPropertiesFile();
		System.out.println(test.OR.getProperty("testname"));
		
	}
}
