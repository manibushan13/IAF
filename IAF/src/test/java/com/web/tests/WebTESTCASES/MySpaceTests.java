package com.web.tests.WebTESTCASES;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innominds.itaf.driverinit.DriverManager;
import com.innominds.itaf.frameworkengine.ConfigTestFixtures;
import com.innominds.itaf.frameworkengine.PageActionUtils;
import com.innominds.itaf.utils.PropertyFileUtils;
import com.web.tests.myspace.login.Login;
import com.web.tests.myspace.login.LoginDP;
import com.web.tests.myspace.myprofile.AddSkills;
import com.web.tests.myspace.myprofile.SkillsDP;
import com.web.tests.myspace.myrequests.ApplyWFH;
import com.web.tests.myspace.myrequests.ApplyWFHDP;



public class MySpaceTests {
	
	
	Logger logger = Logger.getLogger(MySpaceTests.class.getName());
	    
	private DriverManager driverManager;
	WebDriver driver;
	ConfigTestFixtures config;

	
	
	@BeforeClass(alwaysRun = true)
	public void init() {
		System.out.println("===========================================================");
		System.out.println("==================== Web Automation Started ==========================================");
		System.out.println("===========================================================");
	}
	
	@BeforeMethod(alwaysRun = true)
	public void testPreRequisites() throws Exception {
		driverManager = new DriverManager();
		driver = DriverManager.getDriver();
		DriverManager.setupSeleniumEnvironment(PropertyFileUtils.getPropValuesFromConfig("browser"));
		DriverManager.printEnvInfo();
		config = new ConfigTestFixtures();
		

		
		
	}
	
	
	/*************************************************************************
     * 
     * TEST SUITE TESTS
     * 
     *************************************************************************/
	 
	/*
	 * ****************************************
	 * Objects Creation
	 * ****************************************
	 */
	
	Login login = new Login();
	AddSkills skills = new AddSkills();
	ApplyWFH wfh = new ApplyWFH();

	
	/*
	 * ****************************************
	 * Data Providers
	 * ****************************************
	 */
	
	@DataProvider(name="LoginDP")
	 public Object[][] LoginDP() {
	        return LoginDP.createDP();
	    }
	
	@DataProvider(name="SkillsDP")
	 public Object[][] SkillsDP() {
	        return SkillsDP.createDP();
	    }
	
	@DataProvider(name="ApplyWFHDP")
	 public Object[][] ApplyWFHDP() {
	        return ApplyWFHDP.createDP();
	    }




	/*
	 * ****************************************
	 *   @Test Methods
	 * ****************************************
	 */
	
	
	
	@Test(dataProvider = "LoginDP", enabled = true, groups = { "Regression" })
	public void Login_Invalid(LoginDP dp)
	{
		login.inValidCredentials(dp, driver);
        
    }
	
	
	@Test(dataProvider = "LoginDP", enabled = true, groups = { "Regression" })
	public void Login_Valid(LoginDP dp)
	{
		login.validCredentials(dp, driver);
        
    }

	@Test(dataProvider = "SkillsDP", enabled = true, groups = { "Regression" })
	public void Profile_AddSkills(SkillsDP dp)
	{
		skills.addSkill(dp, driver);
        
    }
	
	@Test(dataProvider = "ApplyWFHDP", enabled = true, groups = { "Regression" })
	public void Apply_WorkFrom_Home(ApplyWFHDP dp) throws FileNotFoundException, IOException
	{
		wfh.applyWorkFromHome(dp, driver);
        
    }


	

	
	
	
	/**
	 * Destroy.
	 * 
	 * @param result
	 *            the result
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@AfterMethod(alwaysRun = true)
	public void destroy(ITestResult result) throws FileNotFoundException, IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Test " + result.getName() + " Failed, Snapshot is being taken");
			PageActionUtils.captureScreenshot(result.getName());
		}
		driverManager.closeBrowser();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("===========================================================");
		System.out.println("==================== Web Automation Completed ==============================");
		System.out.println("===========================================================");
	}


}
