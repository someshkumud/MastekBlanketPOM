package com.test.automation.MastekBlanketPOM.uiActions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	public static final Logger log=Logger.getLogger(LoginPage.class.getName());
	
	WebDriver driver;
	
	@FindBy(id="txtUsername")
	WebElement txtUsername;
	
	@FindBy(id="txtPassword")
	WebElement txtPassword;
	
	@FindBy(id="btnLogin")
	WebElement btnLogin;
	
	@FindBy(id="spanMessage")
	WebElement spanMessage;
	
	@FindBy(xpath="//a[@id='welcome']")
	WebElement welcomeMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginToApplication(String username, String password) {
		txtUsername.clear();
		txtUsername.sendKeys(username);
		log.info("entered username - "+username+" and object is "+txtUsername.toString());
		txtPassword.clear();
		txtPassword.sendKeys(password);
		log.info("entered password - "+password+" and object is "+txtPassword.toString());
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnLogin.click();
		log.info("clicked login button");
	}
	
	public String verifyLogin(){
			try {
				return spanMessage.getText();
			} catch (Exception e) {
				return welcomeMessage.getText();
			}
		
		
	}
	
	public void navigationMenu(String menuName) {
		
	}
	

}
