package com.web.tests.myspace.login;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.itaf.driverinit.DriverManager;
import com.innominds.itaf.frameworkengine.PageActionUtils;

public class Login extends PageActionUtils
{
	



	Logger logger = Logger.getLogger(Login.class.getName());
	WebElement element = null;
	
	
	
	
	public void  inValidCredentials(LoginDP dp, WebDriver driver)
	{
		
		try
		{
		
			Reporter.log("Visit MySpace Login URL", true);
		
			DriverManager.initDriverWithURL(DriverManager.getCurrentBrowserType(), dp._configData().get("web.app.url"));
			
			
			Reporter.log("........Validate Login Header........", true);
			element = getWebElement(dp.or, "LoginHeader");
			String data = dp.td.get("LOGINHEADER");
			Assert.assertTrue(element.getText().trim().equals(data), "Not match");
			
			
			Reporter.log("........Validate Login with Empty UserName and Password........", true);
			jsClick(getWebElement(dp.or, "LoginButton"));
			Assert.assertTrue(getTextFromJSAlert(true).equals(dp.td.get("MSG_WITHOUT_USER")), "Validation failed");
			
			Reporter.log("........Validate Login with Empty UserName and Any Password........", true);
			clearText(getWebElement(dp.or, "Username"));
			enterText(getWebElement(dp.or, "Password"), dp.td.get("GOOD_PASSWORD"));
			jsClick(getWebElement(dp.or, "LoginButton"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("MSG_WITHOUT_USER")), "Validation failed");
			
			Reporter.log("........Validate Login with Any UserName and Empty Password........", true);
			enterText(getWebElement(dp.or, "Username"), dp.td.get("GOOD_USERNAME"));
			clearText(getWebElement(dp.or, "Password"));
			jsClick(getWebElement(dp.or, "LoginButton"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("MSG_WITHOUT_PWD")), "Validation failed");
			
			Reporter.log("........Validate Login with Bad UserName and Bad Password........", true);
			enterText(getWebElement(dp.or, "Password"), dp.td.get("BAD_PASSWORD"));
			enterText(getWebElement(dp.or, "Username"), dp.td.get("BAD_USERNAME"));
			jsClick(getWebElement(dp.or, "LoginButton"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("INVALID_LOGIN_ERRMSG")), "Validation failed");
			
			Reporter.log("........Validate Login with Good UserName and Bad Password........", true);
			enterText(getWebElement(dp.or, "Password"), dp.td.get("BAD_PASSWORD"));
			enterText(getWebElement(dp.or, "Username"), dp.td.get("GOOD_USERNAME"));
			jsClick(getWebElement(dp.or, "LoginButton"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("INVALID_LOGIN_ERRMSG")), "Validation failed");
			
			Reporter.log("........Validate Login with Bad UserName and Good Password........", true);
			enterText(getWebElement(dp.or, "Password"), dp.td.get("GOOD_PASSWORD"));
			enterText(getWebElement(dp.or, "Username"), dp.td.get("BAD_USERNAME"));
			jsClick(getWebElement(dp.or, "LoginButton"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("INVALID_LOGIN_ERRMSG")), "Validation failed");
	
			
		} catch (Exception e) {
			
			Assert.assertFalse(false, "Test case failed due to exception "+e.getMessage());
		}
		

	}
	
	
	public void validCredentials(LoginDP dp, WebDriver driver)
	{
		
		try
		{
		
			Reporter.log("Visit MySpace Login URL", true);
		
			DriverManager.initDriverWithURL(DriverManager.getCurrentBrowserType(), dp._configData().get("web.app.url"));
			
			Reporter.log("........Validate Login with Good UserName and Good Password........", true);
			enterText(getWebElement(dp.or, "Password"), dp.td.get("GOOD_PASSWORD"));
			enterText(getWebElement(dp.or, "Username"), dp.td.get("GOOD_USERNAME"));
			jsClick(getWebElement(dp.or, "LoginButton"));
			
			
			Reporter.log("........Validate Home Page after Login........", true);
			Assert.assertTrue(assertPageTitle(dp.td.get("HOME_TITLE")), "Home page title not matched");
	
			
			
			
		} catch (Exception e) {
			
			Assert.assertFalse(false, "Test case failed due to exception "+e.getMessage());
		}
		

	}

	
	
	
	
   
}
