package com.test.automation.MastekBlanketPOM.testBase;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.automation.MastekBlanketPOM.excelReader.ExcelReader;

public class TestBase {

	//public static Properties Repository=new Properties();
	//public File file;
	//public FileInputStream fileInput;
	public static final Logger log=Logger.getLogger(TestBase.class.getName());
	public WebDriver driver;
	String browser="chrome";
	String url="http://opensource.demo.orangehrmlive.com/index.php/auth/login";
	ExcelReader excel;
	
	//Properties properties;
	
	public void init(){
		//loadPropertiesFile();
		selectBrowser(browser);
		getURL(url);
		String log4jConfigPath="log4j.properties";
		PropertyConfigurator.configure(log4jConfigPath);

	}

	/*public void loadPropertiesFile() throws IOException{
		file=new File(System.getProperty("user.dir")+"\\src\\test\\java\\com\\mastek\\MastekBlanket\\config\\config.properties");
		fileInput=new FileInputStream(file);
		Repository.load(fileInput);
	}*/
	
		
	public void selectBrowser(String browser) {
		
		if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			driver =new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if(browser.equalsIgnoreCase("IE"))
		{
			driver=new InternetExplorerDriver();
		}	
		log.info("Creating object of "+browser);		
	}
	
	public void getURL(String url) {
		log.info("Navigating to "+url);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
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
	
}
