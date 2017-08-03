package com.test.automation.MastekBlanketPOM.uiActions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;
import com.test.automation.MastekBlanketPOM.testBase.TestBase;



public class Leave extends TestBase {
	
	public static final Logger log=Logger.getLogger(LoginPage.class.getName());
	
	WebDriver driver;
	private static List<WebElement> tableData = null;
	public static boolean menuSelected=false;
	//private List<List<String>> actualData=null;
	
	
	public Leave(WebDriver driver) {
		this.driver=driver;
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}
	
	@FindBy(id="welcome")
	private WebElement welcome;
	
	
	
	@FindBy(id="entitlements_filters_bulk_assign")
	private WebElement multipleEmployee;
	
	@FindBy(id="entitlements_filters_location")
	private WebElement empLocation;
	
	@FindBy(id="entitlements_filters_subunit")
	private WebElement empSubunit;
	
	@FindBy(id="entitlements_employee_empName")
	private WebElement employeeName;
	
	
	@FindBy(id="entitlements_leave_type")
	private WebElement leaveType;
	
	@FindBy(id="period")
	private WebElement leavePeriod;
	
	@FindBy(id="entitlements_entitlement")
	private WebElement leaveEntitlement;
	
	@FindBy(id="btnSave")
	private WebElement btnSave;
	
	@FindBy(id="dialogConfirmBtn")
	private WebElement btnDialogConfirm;
	
	@FindBy(id="dialogUpdateEntitlementConfirmBtn")
	private WebElement btnUpdateEntitlementConfirm;
	
	@FindBy(xpath="//table[@id='resultTable']/tbody/tr/td[5]")
	private WebElement leaveEntitled;
	
	@FindBy(xpath="//ol[@id='employee_entitlement_update']/span")
	private WebElement entitlementUpdateConfirm;
	
	@FindBy(id="assignleave_txtEmployee_empName")
	private WebElement txtEmployee;
	
	@FindBy(id="assignleave_txtLeaveType")
	private WebElement dropdownLeaveType;
	
	@FindBy(id="leaveBalance_details_link")
	private WebElement viewDetailsLink;
	
	@FindBy(id="confirmOkButton")
	private WebElement confirmOkButton;
	
	@FindBy(id="assignleave_leaveBalance")
	private WebElement leaveBalance;
	
	
	@FindBy(id="confirmCancelButton")
	private WebElement confirmCancelButton;
	
	@FindBy(linkText="Assign Leave")
	private WebElement assignLeave;
	
	@FindBy(id="assignleave_partialDays")
	private WebElement dropdownPartialDays;
	
	@FindBy(id="assignleave_txtComment")
	private WebElement txtComment;
	
	@FindBy(id="assignBtn")
	private WebElement btnAssign;
	
	@FindBy(id="assignleave_firstDuration_duration")
	private WebElement dropdownDuration;

	@FindBy(id="assignleave_firstDuration_ampm")
	private WebElement dropdownAMPM;
	
	@FindBy(id="assignleave_secondDuration_duration")
	private WebElement dropdownDurationSecond;

	@FindBy(id="assignleave_secondDuration_ampm")
	private WebElement dropdownAMPMSecond;
	
	
	@FindBy(xpath="//form[@id='frmLeaveApply']/fieldset/ol/li[1]/span")
	public WebElement velidateEmpName;
	
	@FindBy(xpath="//form[@id='frmLeaveApply']/fieldset/ol/li[5]/span")
	public WebElement velidateToDate;
	
	@FindBy(id="assignleave_firstDuration_time_from")
	public WebElement asssignLeaveTimeFrom;
	
	@FindBy(id="assignleave_firstDuration_time_to")
	public WebElement asssignLeaveTimeTo;
	
	@FindBy(id="assignleave_secondDuration_time_from")
	public WebElement asssignLeaveTimeFromSecond;
	
	@FindBy(id="assignleave_secondDuration_time_to")
	public WebElement asssignLeaveTimeToSecond;
	
	@FindBy(xpath="//span[@id='assignleave_firstDuration_specify_time_content']/span")
	public WebElement velidateStartDay;
	
	@FindBy(xpath="//span[@id='assignleave_secondDuration_specify_time_content']/span")
	public WebElement velidateEndDay;

	@FindBy(xpath="//div[@id='wrapper']/div[2]/ul")
	private WebElement allMenus;
	
	
	@FindBy(xpath="//form[@id='frmLeaveApply']/fieldset/ol/li[4]/img")
	public WebElement fromDate;

	@FindBy(xpath="//form[@id='frmLeaveApply']/fieldset/ol/li[5]/img")
	public WebElement toDate;
	
	@FindBy(xpath="//div[@id='ui-datepicker-div']/div/div/select[2]")
	public WebElement datePickerYear;
	
	@FindBy(xpath="//div[@id='ui-datepicker-div']/div/div/select[1]")
	public WebElement datePickerMonth;
	
	@FindBy(xpath="//table[@class='ui-datepicker-calendar']/tbody")
	public WebElement datePickerTable;
	
	@FindBy(xpath="//div[@id='leaveBalanceConfirm']/div[2]/p[1]")
	public WebElement confirmInsufficientLeave;	
	
	@FindBy(xpath="//div[@id='balance_details']/div[2]/dl")
	private WebElement leaveBalanceSummary;	
	
	@FindBy(id="spanMessage")
	public WebElement loginErrorMessage;	
	
	@FindBy(xpath="//div[@id='balance_details']/div[2]/table")
	private WebElement tblLeave;	
	
	@FindBy(xpath="//div[@id='content']/div[1]/div[2]/table")
	private WebElement tblOverlappedLeave;	
	

	public void enterEmployeeName(String name){
		txtEmployee.sendKeys(name);
		
	}
	
	public void selectLeaveType(String value){
		Select leaveType = new Select(dropdownLeaveType);  
		leaveType.selectByVisibleText(value);
	}


	public void selectMenuOptimized(String menu){
		
		String arMenu[]=menu.split(">");			
		int menuIndex=0;
		menuNevigate(allMenus,arMenu,menuIndex);		
		menuSelected=false;
		
	}
	
	private void menuNevigate(WebElement allMenus,String arMenu[],int menuIndex){
		//,boolean menuSelected
		if(menuSelected!=true){		
			List<WebElement> parentMenus=new ArrayList<WebElement>();
			Actions action = new Actions(driver);	
			int lastElement=arMenu.length-1;
			parentMenus=allMenus.findElements(By.tagName("li"));
			
			for (WebElement parentMenu : parentMenus) {
				if(menuSelected==true){
					break;
				}else{
					String parentMenuText=parentMenu.getText();
					if (parentMenuText.equalsIgnoreCase(arMenu[menuIndex])) {
						if(menuIndex==lastElement){
							parentMenu.click();
							menuSelected=true;
							break;
							
						}
						else{
							action.moveToElement(parentMenu).build().perform();
							menuIndex++;
							menuNevigate(parentMenu,arMenu,menuIndex);
							//, menuSelected
						}			
						
					}
				}
			}
		}
	}
	
	public void selectDate(String dateType, String date){
		String arDate[]=date.split("-");
		String dd=arDate[2];
		int mm=Integer.parseInt(arDate[1]);
		String yyyy=arDate[0];
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (dateType.equalsIgnoreCase("from")) {
			fromDate.click();
			
		}else if(dateType.equalsIgnoreCase("to")){
			toDate.click();
		}
		//****Select year
		Select dpYear = new Select(datePickerYear);  
		dpYear.selectByVisibleText(yyyy);
		
		//*****Select month
		Select dpMonth = new Select(datePickerMonth);  
		dpMonth.selectByIndex(mm-1);

		//****Select Date
		//datePickerTable
		
		tableData=readDateTable(datePickerTable,dd);
		
		for (int i = 0; i < tableData.size(); i++) {
			if (tableData.get(i).getAttribute("disabled") == null) {
				tableData.get(i).click();
				break;
			} 
		}

		
	}
	
	
	public void selectDuration(String partialDay, String ampm){
		Select dpDuration = new Select(dropdownDuration);  
		dpDuration.selectByVisibleText(partialDay);
		
		Select dpAMPM = new Select(dropdownAMPM);  
		dpAMPM.selectByVisibleText(ampm);
		
	}
	

	
	public List<List<String>> verifyLeaveBalanceSummary(){
		List<List<String>> actualData=new ArrayList<List<String>>();		
		List<WebElement> dt_All=leaveBalanceSummary.findElements(By.tagName("dt"));
		List<WebElement> dd_All=leaveBalanceSummary.findElements(By.tagName("dd"));
		
		for(int i = 0; i < dt_All.size(); i++){
			List<String> rowData=new ArrayList<String>();
			System.out.println("|"+dt_All.get(i).getText()+"|"+dd_All.get(i).getText()+"|");
			rowData.add(dt_All.get(i).getText());
			rowData.add(dd_All.get(i).getText());
			actualData.add(rowData);
		}
		return actualData;		
	}
	
	public List<List<String>> readTableData(String type){	
			if(type.equalsIgnoreCase("Leave")){
				return readTableDataSkipEmptyRow(tblLeave,0,0);	
			}else {		
				return readTableData(tblOverlappedLeave,0,0);
			}
		}
	
	public String addLeaveEntitlement(String multipleEmp,String location,String subUnit,String empName,String leaveType, String leavePeriod, String entitlement){
		if(multipleEmp.equalsIgnoreCase("Y")){
			multipleEmployee.click();
			 
			selectByPartOfVisibleText(empLocation, location);
			//dpLocation.selectByVisibleText(".*"+location+".*");
			Select dpSubunit = new Select(empSubunit);  
			dpSubunit.selectByVisibleText(subUnit);
		}else{
			employeeName.sendKeys(empName);
			employeeName.sendKeys(Keys.TAB);
		}
		
		Select dpLeaveType = new Select(this.leaveType);  
		dpLeaveType.selectByVisibleText(leaveType);
		
		Select dpLeavePeriod = new Select(this.leavePeriod);  
		dpLeavePeriod.selectByVisibleText(leavePeriod);
		
		entitlement=removeDecimalFromString(entitlement);
		
		leaveEntitlement.sendKeys(entitlement);
		
		btnSave.click();
		try {
			//waitForElement(10000,btnDialogConfirm);
			Thread.sleep(5000);
			if(btnDialogConfirm.isDisplayed())
			btnDialogConfirm.click();
			else if(btnUpdateEntitlementConfirm.isDisplayed())
				
					{
						String temp[]=entitlementUpdateConfirm.getText().split(" ");
						entitlement=removeDecimalFromString(temp[temp.length-1]);
					}
				btnUpdateEntitlementConfirm.click();
		} catch (Exception e) {
			
		}
		
		return entitlement;
		
	}
	
	public String leaveEntitled() throws Exception{
		return leaveEntitled.getText();
	}

	public String removeDecimalFromString(String str) {
		if(str.contains(".")) {
			String temp[] = str.split("\\.");
			str= temp[0];
		}
		return str;
	}
	
	public void selectDropdown(WebElement element, String value){
		
		try {
			if(isElementPresent(element)){
			Select dropdown = new Select(element);  
			dropdown.selectByVisibleText(value);
			}
		} catch (Exception e) {

		}

	}
	
	public boolean isElementPresent(WebElement element)  
	 {  
	               driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
	               try  
	               {  
	                    if(element.isDisplayed())
	                    	return true;  
	                    else
	                    	return false;
	               }  
	               catch(NoSuchElementException e)  
	               {  
	                   return false;  
	               }  
	              finally  
	              {  
	                  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
	              }  
	    }
	
	
	public String getLeaveBalance(){
		String leaveBalance="";
		
		leaveBalance=this.leaveBalance.getText().split("\\.")[0];		
		return leaveBalance;
	}
	
	public String assignLeave(String empName,String leaveType, String fromDate, String toDate,String partDays, 
			String duration,String amPM,String durationSec,String amPMSec,String startDayFrom,String startDayTo,
			String endDayFrom,String endDayTo, String comment){
		txtEmployee.clear();
		txtEmployee.sendKeys(empName);
		txtEmployee.sendKeys(Keys.TAB);
		
		selectDropdown(this.dropdownLeaveType,leaveType);
				
		selectDate("from", fromDate);
		
		selectDate("to", toDate);
		
		selectDropdown(this.dropdownPartialDays,partDays);
		
		selectDropdown(this.dropdownDuration,duration);		
		
		selectDropdown(this.dropdownAMPM,amPM);
	
		selectDropdown(this.dropdownDurationSecond,durationSec);
		
		selectDropdown(this.dropdownAMPMSecond,amPMSec);

		selectDropdown(this.asssignLeaveTimeFrom,startDayFrom);
		
		selectDropdown(this.asssignLeaveTimeTo,startDayTo);

		selectDropdown(this.asssignLeaveTimeFromSecond,endDayFrom);
		
		selectDropdown(this.asssignLeaveTimeToSecond,endDayTo);		
		
		txtComment.clear();
		txtComment.sendKeys(comment);
		txtComment.sendKeys(Keys.TAB);
		
		String actualError=verifyAssignLeaveError();
		if(actualError!=""){
			return actualError;
		}else
		{
		btnAssign.click();		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
		}
		if(isElementPresent(confirmOkButton))
		confirmOkButton.click();
		
		return "";
		}
		
	}
	
	public String verifyAssignLeaveError(){

				if(isElementPresent(velidateEmpName))
					return velidateEmpName.getText();
				else if(isElementPresent(velidateToDate))
					return velidateToDate.getText();
				else if(isElementPresent(velidateStartDay))
					return velidateStartDay.getText();
				else if(isElementPresent(velidateEndDay))
					return velidateEndDay.getText();
				else
					return "";	
		
	}
	
}
