package com.test.automation.MastekBlanketPOM.assignLeave;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.test.automation.MastekBlanketPOM.testBase.ExtentManager;
import com.test.automation.MastekBlanketPOM.testBase.TestBase;
import com.test.automation.MastekBlanketPOM.uiActions.Leave;
import com.test.automation.MastekBlanketPOM.uiActions.LoginPage;

public class TC003_AssignLeave extends TestBase {
	public static final Logger log=Logger.getLogger(TC003_AssignLeave.class.getName());
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
		String[][] testRecords = getData("AssignLeave.xlsx", "AssignLeave");
		return testRecords;
	}
	

	@Test(dataProvider="TestData")
	public void assignLeave(String execute,String testCase,String empName, String leaveType, String fromDate, String toDate, String partDays, String duration, String amPM, String durationSec, String amPMSec, String startDayFrom, String startDayTo, String endDayFrom, String endDayTo, String comment, String expectedLeaveBalance,String error){
		String actualLeaveBalance="";
		String actualError="";
		
		if(execute.equalsIgnoreCase("N")){
			throw new SkipException("Skipping test as per data provided");
		}
		log.info("============Starting Test for Test Case : "+testCase+" for Leave Assignment=============");
		
		leavePage.selectMenuOptimized("Leave>Assign Leave");
		log.info("Menu : Leave>Assign Leave selected");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//currentLeaveBalance=leavePage.getLeaveBalance();
		
		actualError=leavePage.assignLeave(empName, leaveType, fromDate, toDate, partDays, duration, amPM, durationSec, amPMSec, startDayFrom, startDayTo, endDayFrom, endDayTo, comment);
		
		actualLeaveBalance=leavePage.getLeaveBalance();

		try {
			if(expectedLeaveBalance!="")
			Assert.assertEquals(actualLeaveBalance, expectedLeaveBalance);
			
		} catch (Exception e) {
			
			
		}finally {
			try{
				if(error!="")
				Assert.assertEquals(actualError, error);
			}catch(Exception e1){
				
			}
			finally{
			leavePage.selectMenuOptimized("Dashboard");
			log.info("============Finished Test for Test Case : "+testCase+" for Leave Assignment=============");
			}
		}
	}
	
	

}
