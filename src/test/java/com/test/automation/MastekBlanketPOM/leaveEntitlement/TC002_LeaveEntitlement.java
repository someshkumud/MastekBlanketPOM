package com.test.automation.MastekBlanketPOM.leaveEntitlement;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.test.automation.MastekBlanketPOM.testBase.TestBase;
import com.test.automation.MastekBlanketPOM.uiActions.Leave;
import com.test.automation.MastekBlanketPOM.uiActions.LoginPage;

public class TC002_LeaveEntitlement extends TestBase {
	public static final Logger log=Logger.getLogger(TC002_LeaveEntitlement.class.getName());
	LoginPage loginpage;
	Leave leavePage;
	
	@BeforeClass
	public void setUp() throws IOException{
		init();
		loginpage=new LoginPage(driver);
		leavePage=new Leave(driver);
		loginpage.loginToApplication("admin", "admin");
	}
	
	@DataProvider(name="TestData")
	public String[][] getTestData(){
		String[][] testRecords = getData("LeaveEntitlement.xlsx", "LeaveEntitlement");
		return testRecords;
	}
	//
	//new Leave(driver)
	@Test(dataProvider="TestData")
	public void leaveEntitlement(String execute,String testCase,String multipleEmp, String location, String subUnit, String empName, String leaveType, String leavePeriod, String entitlement){
		if(execute.equalsIgnoreCase("N")){
			throw new SkipException("Skipping test as per data provided");
		}
		log.info("============Starting Test for Test Case : "+testCase+" for Leave Entitlement=============");
		
		Leave.menuSelected=false;
		leavePage.selectMenuOptimized("Leave>Entitlements>Add Entitlements");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String expectedLeavesEntitled=leavePage.addLeaveEntitlement(multipleEmp, location, subUnit, empName, leaveType, leavePeriod, entitlement);
		String actualLeaveEntitled=leavePage.leaveEntitled();
		actualLeaveEntitled=leavePage.removeDecimalFromString(actualLeaveEntitled);
		Assert.assertEquals(actualLeaveEntitled, expectedLeavesEntitled);
		//getScreenShot("LeaveEntitlement_"+testCase);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("============Finished Test for Test Case : "+testCase+" for Leave Entitlement=============");
	}
	
	

}
