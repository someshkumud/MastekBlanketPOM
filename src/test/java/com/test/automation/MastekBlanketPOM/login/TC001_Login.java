package com.test.automation.MastekBlanketPOM.login;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.automation.MastekBlanketPOM.testBase.TestBase;
import com.test.automation.MastekBlanketPOM.uiActions.LoginPage;

public class TC001_Login extends TestBase {
	public static final Logger log=Logger.getLogger(TC001_Login.class.getName());
	LoginPage loginpage;
	
	@BeforeClass
	public void setUp(){
		init();
	}
	
	
	@Test
	public void login(){
		log.info("============Starting Test : Login=============");
		loginpage=new LoginPage(driver);
		loginpage.loginToApplication("testUsername", "testpassword");
		Assert.assertEquals(loginpage.getLoginFailedText(), "Invalid credentials");
		log.info("============Finished Test : Login=============");
	}
	
	@AfterClass
	public void endTest(){
		driver.close();
	}
}
