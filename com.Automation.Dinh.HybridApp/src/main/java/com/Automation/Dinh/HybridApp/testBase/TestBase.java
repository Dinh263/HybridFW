package com.Automation.Dinh.HybridApp.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class TestBase {
	public WebDriver driver;
	public Properties OR;
	public File file;
	public FileInputStream inputStr;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	
	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir")+"/src/main/java/com/Automation/Dinh/HybridApp/report/test"+formater.format(calendar.getTime())+".html",false);
	}
	
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
		
		file= new File(System.getProperty("user.dir")+"/src/main/java/com/Automation/Dinh/HybridApp/properties/homepage.properties");
		inputStr = new FileInputStream(file);
		OR.load(inputStr);
	}
	
	public void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public String getScreenShot(String imageName) throws IOException {
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
		return actualImageName;
	}
	public void getPropertiesData() {
		
	}
	
	public static void main(String[]args) throws Exception {
		TestBase test = new TestBase();
		test.loadPropertiesFile();
		test.getWebElement("username");
		
	}
	
	public void getResult(ITestResult result) throws IOException {
		if(result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, " test case is pass");
		}
		else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, " test case is skipeed and skip reason is : "+result.getThrowable());
		}
		else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, " test case is faled and failured reason is : "+result.getThrowable());
			String screen = getScreenShot("");
			test.log(LogStatus.FAIL,test.addScreenCapture(screen));
			
		}
		else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName()+" test is started");
		}		
		
	}
	@AfterMethod()
	public void afterMethod(ITestResult result) throws IOException {
		getResult(result);
	}
	
	@BeforeMethod()
	public void beforeMethod(ITestResult result) throws IOException {
		test =extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+" test started");
	}
	
	@AfterClass(alwaysRun = true)
	public void endTest() {
		driver.quit();
		extent.endTest(test);
		extent.flush();
	}
	
	public  WebElement getLocator(String locator) throws Exception {
		String[]split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		
		System.out.println(locatorType);
		System.out.println(locatorValue);
		
		if(locatorType.toLowerCase().equals("id")) {
			return driver.findElement(By.id(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("name")) {
			return driver.findElement(By.name(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("classname") || locator.toLowerCase().equals("class")) {
			return driver.findElement(By.className(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("tagname") || locatorType.toLowerCase().equals("tag")) {
			return driver.findElement(By.tagName(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link") || locatorType.toLowerCase().equals("partiallinktext")) {
			return driver.findElement(By.linkText(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css")) {
			return driver.findElement(By.cssSelector(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("xpath")) {
			return driver.findElement(By.xpath(locatorValue));
		}
		else {
			throw new Exception("Unknow locator type '"+locatorType+"'");
		}
	}
	
	
	public List<WebElement> getLocators(String locator) throws Exception {
		String[]split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		
		if(locatorType.toLowerCase().equals("id")) {
			return driver.findElements(By.id(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("name")) {
			return driver.findElements(By.name(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("classname") || locator.toLowerCase().equals("class")) {
			return driver.findElements(By.className(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("tagname") || locatorType.toLowerCase().equals("tag")) {
			return driver.findElements(By.tagName(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link") || locatorType.toLowerCase().equals("partiallinktext")) {
			return driver.findElements(By.linkText(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css")) {
			return driver.findElements(By.cssSelector(locatorValue));
		}
		else if(locatorType.toLowerCase().equals("xpath")) {
			return driver.findElements(By.xpath(locatorValue));
		}
		else {
			throw new Exception("Unknow locator type '"+locatorType+"'");
		}
	}
	
	public WebElement getWebElement(String locator) throws Exception {
		return getLocator(OR.getProperty(locator));
	}
	
	public List<WebElement> getWebElements(String locator) throws Exception {
		return getLocators(OR.getProperty(locator));
	}
	
	
	
	
}
