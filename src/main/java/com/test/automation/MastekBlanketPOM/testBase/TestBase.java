package com.test.automation.MastekBlanketPOM.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.MastekBlanketPOM.excelReader.ExcelReader;
import com.test.automation.MastekBlanketPOM.listener.WebEventListener;

public class TestBase {

	public static final Logger log=Logger.getLogger(TestBase.class.getName());
	public WebDriver dr;
	public EventFiringWebDriver driver;
	public WebEventListener eventListener;
	public static ExtentReports extent;
	public static ExtentTest test;
	ExcelReader excel;
	public Properties OR=new Properties();
	public ITestResult result;
	
	public EventFiringWebDriver getDriver() {
		return driver;		
	}
	
	static {
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formater=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent=new ExtentReports(System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\automation\\MastekBlanketPOM\\report\\MastekBlanket"+formater.format(cal.getTime())+".html",false);
	}
	
	public void loadPropertiesFile() throws IOException{
		//OR=new Properties();
		File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\automation\\MastekBlanketPOM\\config\\config.properties");
		FileInputStream fileInput = new FileInputStream(file);
		OR.load(fileInput);
	}
	
	public void setDriver(EventFiringWebDriver driver) {
		this.driver=driver;
	}
	
	public void init() throws IOException{
		loadPropertiesFile();
		selectBrowser(OR.getProperty("browser"));
		getURL(OR.getProperty("url"));
		String log4jConfigPath="log4j.properties";
		PropertyConfigurator.configure(log4jConfigPath);
	}


	
		
	public void selectBrowser(String browser) {
		log.info("Creating object of "+browser);
		if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			dr =new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			dr=new ChromeDriver();
		}else if(browser.equalsIgnoreCase("IE"))
		{
			dr=new InternetExplorerDriver();
		}	
		driver=new EventFiringWebDriver(dr);
		eventListener=new WebEventListener();
		driver.register(eventListener);
	}
	
	public void getURL(String url) {
		log.info("Navigating to "+url);
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	

	public String[][] getData(String excelName, String sheetName) {
		String path=System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\automation\\MastekBlanketPOM\\data\\"+excelName;
		excel=new ExcelReader(path);
		String[][] data = excel.getSheetData(sheetName, excelName);
		return data;
	}
	
	public void waitForElement(int timeOutInSeconds,WebElement element) {
		WebDriverWait wait=new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));		
	}

	public String getScreenShot(String name) {
		if(name=="") {
			name="blank";
		}
		File destFile=null;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formater=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			String reportDirectory=new File(System.getProperty("user.dir")).getAbsolutePath()+"\\src\\main\\java\\com\\test\\automation\\MastekBlanketPOM\\screenShot\\";
			destFile=new File(reportDirectory+name+"_"+formater.format(cal.getTime())+".png");
			FileUtils.copyFile(scrFile, destFile);
			Reporter.log("<a href='"+destFile.getAbsolutePath()+"'><img src='"+destFile.getAbsolutePath()+"' height='100' width='100'/></a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
	
	
	public void getAllWindows() {
		
		//To Do : Code to handel window
	}
	
	public void getResult(ITestResult result) {
		if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName()+" test is Pass");
		}else if(result.getStatus()==ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName()+" test is Skipped with Reason :"+result.getThrowable());
		}else if(result.getStatus()==ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName()+" test is Failed with Reason :"+result.getThrowable());
			test.log(LogStatus.FAIL, test.addScreenCapture(getScreenShot("")));
		}else if(result.getStatus()==ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName()+" test is Started");
		}
		
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		getResult(result);
	}
	
	@BeforeMethod
	public void BeforeMethod(Method result) {
		test=extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+" test started");
	}
	
	@AfterClass(alwaysRun=true)
	public void endTest() {
		closeBrowser();
	}
	
	private void closeBrowser() {
		driver.quit();
		log.info("Browser Closed");
		extent.endTest(test);
		extent.flush();		
	}
	
}
