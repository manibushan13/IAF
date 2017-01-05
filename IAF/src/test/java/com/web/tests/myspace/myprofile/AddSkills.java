package com.web.tests.myspace.myprofile;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.itaf.driverinit.DriverManager;
import com.innominds.itaf.frameworkengine.PageActionUtils;

public class AddSkills extends PageActionUtils{
	
	
	Logger logger = Logger.getLogger(AddSkills.class.getName());
	WebElement element = null;
	
	
	
	
	public void  addSkill(SkillsDP dp, WebDriver driver)
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
	
			Reporter.log("........Click on MyProfile Option on Left pane........", true);
			click(getWebElement(dp.or, "MyProfile"));
			
			Reporter.log("........Click on Skills Tab........", true);
			click(getWebElement(dp.or, "Skills"));
			
			Reporter.log("........Enter Core skills summary........", true);
			enterText(getWebElement(dp.or, "CoreSkillsInput"), dp.td.get("CORESKILLS_INPUT"));
			
			Reporter.log("........Click on Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "AddSkillBtn"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("CATEGORY_ERROR")), "Validation failed");
			
			Reporter.log("........Select Category........", true);
			selectByValue(getWebElement(dp.or, "CategoryDrpDwn"), dp.td.get("CATEGORY_SELECT"));
			
			Reporter.log("........Click on Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "AddSkillBtn"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("SKILLSNAME_ERROR")), "Validation failed");

			Reporter.log("........Select Skills Name........", true);
			selectByValue(getWebElement(dp.or, "SkillNameDrpDwn"), dp.td.get("SKILLSNAME_SELECT"));
			
			Reporter.log("........Click on Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "AddSkillBtn"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("SPECIFICSKILLS_ERROR")), "Validation failed");
			
			Reporter.log("........Enter Specific Skills ........", true);
			enterText(getWebElement(dp.or, "SpecificSkillsInput"), dp.td.get("SPECIFICSKILLS_INPUT"));
			
			Reporter.log("........Click on Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "AddSkillBtn"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("PROFICIENCY_ERROR")), "Validation failed");
			
			Reporter.log("........Select Proficiency Value........", true);
			selectByValue(getWebElement(dp.or, "ProfiencyDrpDwn"), dp.td.get("PROFICIENCY_SELECT"));
			
			Reporter.log("........Click on Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "AddSkillBtn"));
	
			
			
		
			Reporter.log("........Select Category........", true);
			selectByValue(getWebElement(dp.or, "CategoryDrpDwn"), dp.td.get("CATEGORY_SELECT"));
			
			Reporter.log("........Select Skills Name........", true);
			selectByValue(getWebElement(dp.or, "SkillNameDrpDwn"), dp.td.get("SKILLSNAME_SELECT"));
			
			Reporter.log("........Enter Specific Skills ........", true);
			enterText(getWebElement(dp.or, "SpecificSkillsInput"), dp.td.get("SPECIFICSKILLS_INPUT"));
			
			Reporter.log("........Select Proficiency Value........", true);
			selectByValue(getWebElement(dp.or, "ProfiencyDrpDwn"), dp.td.get("PROFICIENCY_SELECT"));
			
			Reporter.log("........Click on Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "AddSkillBtn"));
		
			Reporter.log("........Click on Delete Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "DelLastSkillBtn"));
			Assert.assertTrue(getTextFromJSAlert(true).contains(dp.td.get("SKILL_DELETE")), "Validation failed");
			
			
			Reporter.log("........Click on Add Skill Button and validate message........", true);
			click(getWebElement(dp.or, "SaveBtn"));
			
			Reporter.log("........Validate message after save button........", true);
			click(getWebElement(dp.or, "DelLastSkillBtn"));
			Assert.assertTrue(getWebElement(dp.or, "DataSavedMsg").getText().trim().contains(dp.td.get("SAVED_MSG")), "Validation failed");

		} catch (Exception e) {
			
			Assert.assertFalse(false, "Test case failed due to exception "+e.getMessage());
		}
		

	}


}

