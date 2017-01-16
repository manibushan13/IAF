package com.innominds.itaf.driverinit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.innominds.itaf.frameworkengine.Constants;
import com.innominds.itaf.frameworkengine.WaitForPage;
import com.innominds.itaf.utils.PropertyFileUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * The Class DeviceAppManger.
 * 
 * @author Dileep Mangalapudi
 */
public class DeviceAppManger {
	DesiredCapabilities capabilities;
	@SuppressWarnings("rawtypes")
	AppiumDriver driver;

	/**
	 * Instantiates a new device app manger.
	 */
	public DeviceAppManger() {
		capabilities = new DesiredCapabilities();
	}

	/**
	 * Gets the device driver.
	 *
	 * @return the device driver
	 */
	@SuppressWarnings("rawtypes")
	public AppiumDriver getDeviceDriver() {
		return driver;
	}

	/**
	 * Install app.
	 *
	 * @param env
	 *            the env
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void installApp(String env) throws IOException, InterruptedException {
		System.out.println("Hilti APK Installation Started..........");
		if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
			File file = new File(Constants.APKs_DRIVER_PATH + Constants.APK_FILE);
			try {
				String[] commands = new String[6];
				commands[0] = "cmd.exe";
				commands[1] = "/C";
				commands[2] = "start";
				commands[3] = "adb";
				commands[4] = "install";
				commands[5] = file.getAbsolutePath();
				Process p1 = Runtime.getRuntime().exec(commands);
				p1.waitFor();
				WaitForPage.implicitWait();
				WaitForPage.waitForElementtoLoad();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		WaitForPage.implicitWait();
		System.out.println("Hilti APK Installation completed.................");
	}

	/**
	 * Lauch device driver.
	 *
	 * @param env
	 *            the env
	 * @return the android driver
	 * @throws MalformedURLException
	 *             the malformed URL exception
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("rawtypes")
	public AppiumDriver lauchDeviceDriver(String env) throws MalformedURLException, FileNotFoundException 
	{
		if (env.equalsIgnoreCase("android")) 
		{
			setAndroidCapabilities(env);
			driver = new AndroidDriver(new URL(PropertyFileUtils.getPropValuesFromEnvConfig("appiumServerUrl", env)), capabilities);
		} else if (env.equalsIgnoreCase("ios")) {
			setIosCapabilities(env);
			driver = new IOSDriver(new URL(PropertyFileUtils.getPropValuesFromEnvConfig("appiumServerUrl", env)), capabilities);
		}
		WaitForPage.implicitWait();
		System.out.println(" Hilti App Opened Successfully------------");
		return driver;
	}

	/**
	 * Checks if is app installed.
	 *
	 * @return true, if is app installed
	 * @throws FileNotFoundException 
	 */
	public boolean isAppInstalled(String mobEnv) throws FileNotFoundException {
		Boolean app = driver.isAppInstalled(PropertyFileUtils.getPropValuesFromEnvConfig("bundleId", mobEnv));
		System.out.println("======isAppInstalled :   " + app);
		return app;
	}

	/**
	 * Sets the ios capabilities.
	 * @throws FileNotFoundException 
	 */
	public void setIosCapabilities(String mobEnv) throws FileNotFoundException {
		File ipaFilePath = new File(Constants.IPAs_DRIVER_PATH, Constants.IPAs_INSTALL_FILE);
		System.out.println("App Path:====>" + ipaFilePath.getAbsolutePath());
		capabilities.setCapability("platformName", PropertyFileUtils.getPropValuesFromEnvConfig("platformName", mobEnv));
		capabilities.setCapability("platform", PropertyFileUtils.getPropValuesFromEnvConfig("platform", mobEnv));
		capabilities.setCapability("platformVersion", PropertyFileUtils.getPropValuesFromEnvConfig("platformVersion", mobEnv));
		capabilities.setCapability("deviceName", PropertyFileUtils.getPropValuesFromEnvConfig("deviceName", mobEnv));
		capabilities.setCapability("bundleId", PropertyFileUtils.getPropValuesFromEnvConfig("bundleId", mobEnv));
		capabilities.setCapability("udid", PropertyFileUtils.getPropValuesFromEnvConfig("udid", mobEnv));
		capabilities.setCapability("--full-reset", false);
		capabilities.setCapability("--ipa", ipaFilePath.getAbsolutePath());

		System.out.println(" DeviceName: " + PropertyFileUtils.getPropValuesFromEnvConfig("deviceName", mobEnv) + "\n platformName: "
				+ PropertyFileUtils.getPropValuesFromEnvConfig("platformName", mobEnv) + "\n platformVersion: "
				+ PropertyFileUtils.getPropValuesFromEnvConfig("platformVersion", mobEnv) + "\n bundleId: " + PropertyFileUtils.getPropValuesFromEnvConfig("bundleId", mobEnv)
				+ "\n udid: " + PropertyFileUtils.getPropValuesFromEnvConfig("udid", mobEnv));
	}

	/**
	 * Sets the android capabilities.
	 * @throws FileNotFoundException 
	 */
	public void setAndroidCapabilities(String mobEnv) throws FileNotFoundException 
	{
	
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", PropertyFileUtils.getPropValuesFromEnvConfig("deviceName", mobEnv));
		capabilities.setCapability("platformName", PropertyFileUtils.getPropValuesFromEnvConfig("platformName", mobEnv));
		capabilities.setCapability("platformVersion", PropertyFileUtils.getPropValuesFromEnvConfig("platformVersion", mobEnv));
		capabilities.setCapability("appPackage", PropertyFileUtils.getPropValuesFromEnvConfig("appPackage", mobEnv));
		capabilities.setCapability("appActivity", PropertyFileUtils.getPropValuesFromEnvConfig("appActivity", mobEnv));
		System.out.println(" DeviceName: " + PropertyFileUtils.getPropValuesFromEnvConfig("deviceName", mobEnv) + "\n platformName: " + PropertyFileUtils.getPropValuesFromEnvConfig("platformName", mobEnv) + "\n platformVersion: "
				+ PropertyFileUtils.getPropValuesFromEnvConfig("platformVersion", mobEnv) + "\n appPackage: " + PropertyFileUtils.getPropValuesFromEnvConfig("appPackage", mobEnv)
				+ "\n appActivity: " + PropertyFileUtils.getPropValuesFromEnvConfig("appActivity", mobEnv));
	}

	/**
	 * Uninstall app.
	 *
	 * @param env
	 *            the env
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void uninstallApp(String mobEnv) throws IOException, InterruptedException {
		if (mobEnv.equalsIgnoreCase("android")) {
			try {
				String[] commands = new String[6];
				commands[0] = "cmd.exe";
				commands[1] = "/C";
				commands[2] = "start";
				commands[3] = "adb";
				commands[4] = "uninstall";
				commands[5] = Constants.APK_PACKAGE;
				Process p1 = Runtime.getRuntime().exec(commands);
				p1.waitFor();
			} catch (Exception e) {
				System.err.println(e);
			}
		} else if (mobEnv.equalsIgnoreCase("ios")) {
			driver.removeApp(PropertyFileUtils.getPropValuesFromEnvConfig("bundleId", mobEnv));
		}
		WaitForPage.waitFor5Sec();
		System.out.println("Hilti-App Uninstalled  SUCCESSFULLY-------------");
	}
}
