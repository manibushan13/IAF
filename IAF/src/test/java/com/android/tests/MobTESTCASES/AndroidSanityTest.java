package com.android.tests.MobTESTCASES;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.innominds.itaf.driverinit.AppiumServerManager;
import com.innominds.itaf.driverinit.DeviceAppManger;
import com.innominds.itaf.driverinit.DriverManager;
import com.innominds.itaf.frameworkengine.ConfigTestFixtures;
import com.innominds.itaf.frameworkengine.WaitForPage;

import io.appium.java_client.AppiumDriver;

/**
 * The Class AndroidSanityTest.
 * 
 * @author Dileep Mangalapudi
 */
public class AndroidSanityTest {
	AppiumServerManager appiumServerManager;
	DeviceAppManger deviceAppManger;
	@SuppressWarnings("rawtypes")
	AppiumDriver driver;
	WaitForPage wait;
	ConfigTestFixtures configTestFixtures;

	WebDriver webDriver;
	DriverManager driverManager;
	WaitForPage waitForPage;

	public String browser = "chrome";
	public String webEnv = "puneqa";
	public String mobileEnv = "android";
	/*
	 * public String webEnv = System.getProperty("webEnv"); public String
	 * mobileEnv = System.getProperty("mobileEnv"); public String browser =
	 * System.getProperty(key)
	 */

	@BeforeClass(alwaysRun = true)
	public void init() {
		System.out.println("===========================================================");
		System.out.println("==================== Mobile Automation Started ==========================================");
		System.out.println("===========================================================");
	}

	/**
	 * Android asset manager test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(priority = 1, enabled = true, groups = { "Sanity" })
	public void MobileAutomationTest() throws Exception {
		configTestFixtures = new ConfigTestFixtures();
		configTestFixtures.geRuntimeConfigTestData(webEnv, mobileEnv);
		/*
		 * driverManager = new DriverManager();
		 * driverManager.launchBrowser(browser); webDriver =
		 * driverManager.getDriver(); webLogin = new LoginPage(webDriver,
		 * webEnv); consumable = new AddConsumableAssetPage(); verify = new
		 * WebAssetsPageRU(webDriver); waitForPage = new WaitForPage(webDriver);
		 * driverManager.loadURL(ConfigTestFixtures.getWebAppUrl());
		 * webLogin.webSignIn(ConfigTestFixtures.getWebUsername(),
		 * ConfigTestFixtures.getWebPassword()); consumable.consumableManager();
		 */

		appiumServerManager = new AppiumServerManager();
		deviceAppManger = new DeviceAppManger();
		deviceAppManger.installApp(mobileEnv);
		appiumServerManager.startAppiumServer(mobileEnv);
		deviceAppManger.lauchDeviceDriver(mobileEnv);
		driver = deviceAppManger.getDeviceDriver();
		wait = new WaitForPage(driver);
		appiumServerManager.stopAppiumServer(mobileEnv);
		deviceAppManger.uninstallApp(mobileEnv);
		// verify.webAssetsManager();
	}

	/**
	 * Tear down.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	// @AfterTest(alwaysRun = true)
	/*
	 * public void tearDown() throws IOException, InterruptedException {
	 * driverManager.closeBrowser(); }
	 */

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("===========================================================");
		System.out.println("==================== Mobile Automation Completed ==============================");
		System.out.println("===========================================================");
	}
}
