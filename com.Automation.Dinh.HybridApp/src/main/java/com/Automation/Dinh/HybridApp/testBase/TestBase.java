package com.Automation.Dinh.HybridApp.testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {
	WebDriver driver;
	
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
	
	public static void main(String[]args) {
		TestBase test = new TestBase();
		test.getBrowser("firefox");
	}
}
