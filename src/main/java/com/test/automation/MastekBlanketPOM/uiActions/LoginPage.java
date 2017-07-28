package com.test.automation.MastekBlanketPOM.uiActions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.MastekBlanketPOM.login.TC001_Login;

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
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginToApplication(String username, String password) {
		txtUsername.sendKeys(username);
		log.info("entered username - "+username+" and object is "+txtUsername.toString());
		txtPassword.sendKeys(password);
		log.info("entered password - "+password+" and object is "+txtPassword.toString());
		btnLogin.click();
		log.info("clicked login button and object is "+btnLogin.toString());
	}
	
	public String getLoginFailedText() {
		log.info("error message is : "+spanMessage.getText());
		return spanMessage.getText();		
	}

}