package com.web.tests.myspace.myrequests;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import com.innominds.itaf.driverinit.DriverManager;
import com.innominds.itaf.frameworkengine.CommonUtils;
import com.innominds.itaf.frameworkengine.PageActionUtils;
import com.innominds.itaf.frameworkengine.WaitForPage;

public class ApplyWFH extends PageActionUtils{
	
	Logger logger = Logger.getLogger(ApplyWFH.class.getName());
	WebElement element = null;
	WebDriver dr;
	
	public void  applyWorkFromHome(ApplyWFHDP dp, WebDriver driver)
	{
		
		try
		{
			Reporter.log("Login Into MySpace URL", true);
		
			DriverManager.initDriverWithURL(DriverManager.getCurrentBrowserType(), dp._configData().get("web.app.url"));
			dr = DriverManager.getDriver(); 
			
			Reporter.log("Validate Login Header", true);
			element = getWebElement(dp.or, "LoginHeader");
			String data = dp.td.get("LOGINHEADER");
			Assert.assertTrue(element.getText().trim().equals(data), "Not match");
			
			Reporter.log("Login with Good UserName and Good Password", true);
			enterText(getWebElement(dp.or, "Username"), dp.td.get("GOOD_USERNAME"));
			enterText(getWebElement(dp.or, "Password"), dp.td.get("GOOD_PASSWORD"));
			jsClick(getWebElement(dp.or, "LoginButton"));
			
			Reporter.log("Validate Home Page Header", true);
			WaitForPage.waitFor5Sec();
			element = getWebElement(dp.or, "HomePage");
			String homePageHeader = dp.td.get("HOMEPAGE");
			Assert.assertTrue(element.getText().trim().equals(homePageHeader), "Not match");
			
			Reporter.log("Apply Work From Home", true);
			jsClick(getWebElement(dp.or, "MyRequests"));
			WaitForPage.waitFor5Sec();
			jsClick(getWebElement(dp.or, "ApplyWPH"));
			WaitForPage.waitFor5Sec();
			
			String a[] = CommonUtils.splitString(CommonUtils.getSysTime(), "-");
			
			click(getWebElement(dp.or, "DateField"));
			WaitForPage.waitFor5Sec();
			click(dr.findElement(By.xpath("//a[text()='"+Integer.parseInt(a[0])+"']")));
			//click(getWebElement(dp.or,"ApplyDate"));
			WaitForPage.waitFor5Sec();
			click(getWebElement(dp.or,"ApplyWFHButton"));
			WaitForPage.waitFor5Sec();
			
			Reporter.log("Verify WFH Applied Date.", true);
			jsScrollWindow();
			verifyTextPresent(getWebElement(dp.or, "AppliedDate"), CommonUtils.getSysTime().trim());
			
		} catch (Exception e) {
			
			Assert.assertTrue(false, "Test case failed due to exception "+e.getMessage());
		}
		

	}

}
