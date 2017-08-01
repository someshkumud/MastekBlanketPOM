package com.test.automation.MastekBlanketPOM.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.test.automation.MastekBlanketPOM.excelReader.ExcelReader;

public class TestBase {

	public static final Logger log=Logger.getLogger(TestBase.class.getName());
	public WebDriver dr;
	public EventFiringWebDriver driver;
	ExcelReader excel;
	public Properties OR=new Properties();
	public ITestResult result;
	private static List<WebElement> tblRows=null, tblCols=null, dateElements=null;
	private static List<List<String>> tableData = null;
	private static List<String> colData = null;
	
	
	public EventFiringWebDriver getDriver() {
		return driver;		
	}
	/*
	static {
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formater=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent=new ExtentReports(System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\automation\\MastekBlanketPOM\\report\\MastekBlanket"+formater.format(cal.getTime())+".html",false);
	}
*/
	
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

	
	public void loadPropertiesFile() throws IOException{
		File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\test\\automation\\MastekBlanketPOM\\config\\config.properties");
		FileInputStream fileInput = new FileInputStream(file);
		OR.load(fileInput);
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
/*
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
	*/
	
	public void getAllWindows() {
		

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		//getResult(result);
	}
	
	@BeforeMethod
	public void BeforeMethod(Method result) {
	//	test=extent.startTest(result.getName());
	//	test.log(LogStatus.INFO, result.getName()+" test started");
		
		
	}
	
	@AfterClass(alwaysRun=true)
	public void endTest() {
		closeBrowser();
	}
	
	private void closeBrowser() {
		driver.quit();
		log.info("Browser Closed");
		//extent.endTest(test);
		//extent.flush();		
	}
	
	public static List<WebElement> readDateTable(WebElement WebTableElement,String date){
		int dd=Integer.parseInt(date);
		date=Integer.toString(dd);
		dateElements=new ArrayList<WebElement>();
		tblRows = WebTableElement.findElements(By.tagName("tr"));
		for(int i=0; i < tblRows.size(); i++){
			WebElement row = tblRows.get(i);
			tblCols = row.findElements(By.tagName("td"));
			for(int j=0; j < tblCols.size(); j++)			
				if (tblCols.get(j).getText().equalsIgnoreCase(date)) {
					dateElements.add(tblCols.get(j));
				} 
		}		
		return dateElements;
	}	
	
	public static List<List<String>> readTableData(WebElement WebTableElement){
		tableData = new ArrayList<List<String>>();
		tblRows = WebTableElement.findElements(By.tagName("tr"));
		for(int i=0; i < tblRows.size(); i++){
			WebElement row = tblRows.get(i);
			colData = new ArrayList<String>();
			tblCols = row.findElements(By.tagName("td"));
			for(int j=0; j < tblCols.size(); j++)			
				colData.add(tblCols.get(j).getText());			
			tableData.add(colData);
		}		
		return tableData;
	}	
	
	
	public static List<List<String>> readTableDataSkipEmptyRow(WebElement WebTableElement, int startRow, int startCol){
		tableData = new ArrayList<List<String>>();
		tblRows = WebTableElement.findElements(By.tagName("tr"));
		for(int i=startRow; i < tblRows.size(); i++){
			WebElement row = tblRows.get(i);
			colData = new ArrayList<String>();
			tblCols = row.findElements(By.tagName("td"));
			for(int j=startCol; j < tblCols.size(); j++){	
				if(!tblCols.get(j).getText().equalsIgnoreCase("")){
				colData.add(tblCols.get(j).getText());	
				}
			}
			if(colData.size()!=0){
			tableData.add(colData);
			}
		}		
		return tableData;
	}
	
	public static List<List<String>> readTableData(WebElement WebTableElement, int startRow, int startCol){
		tableData = new ArrayList<List<String>>();
		tblRows = WebTableElement.findElements(By.tagName("tr"));
		for(int i=startRow; i < tblRows.size(); i++){
			WebElement row = tblRows.get(i);
			colData = new ArrayList<String>();
			tblCols = row.findElements(By.tagName("td"));
			if(tblCols.size()==0){
				tblCols = row.findElements(By.tagName("th"));
			}			
			for(int j=startCol; j < tblCols.size(); j++)			
				colData.add(tblCols.get(j).getText());			
			tableData.add(colData);
			
		}		
		return tableData;
	}
	
	public void selectByPartOfVisibleText(WebElement element, String partValue) {
		Select dpElement = new Select(element);
	    List<WebElement> optionElements = element.findElements(By.tagName("option"));
	    for (WebElement optionElement: optionElements) {
	        if (optionElement.getText().contains(partValue)) {
	            String optionIndex = optionElement.getAttribute("index");
	            dpElement.selectByIndex(Integer.parseInt(optionIndex));
	            break;
	        }
	    }

	}
}
