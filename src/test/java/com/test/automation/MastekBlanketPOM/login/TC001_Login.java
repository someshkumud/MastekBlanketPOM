package com.test.automation.MastekBlanketPOM.login;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.automation.MastekBlanketPOM.testBase.TestBase;
import com.test.automation.MastekBlanketPOM.uiActions.LoginPage;

public class TC001_Login extends TestBase {
	public static final Logger log=Logger.getLogger(TC001_Login.class.getName());
	LoginPage loginpage;
	
	@BeforeClass
	public void setUp() throws IOException{
		init();
	}
	
	@DataProvider(name="TestData")
	public String[][] getTestData(){
		String[][] testRecords = getData("Login.xlsx", "Login");
		return testRecords;
	}
	
	
	@Test(dataProvider="TestData")
	public void login(String execute,String testCase,String username, String password, String message){
		if(execute.equalsIgnoreCase("N")){
			throw new SkipException("Skipping test as per data provided");
		}
		log.info("============Starting Test for Test Case : "+testCase+"=============");
		loginpage=new LoginPage(driver);
		loginpage.loginToApplication(username, password);
		Assert.assertEquals(loginpage.verifyLogin(), message);
		getScreenShot("TestLogin_"+testCase);
		log.info("============Finished Test for Test Case : "+testCase+"=============");
	}
	
	
}
