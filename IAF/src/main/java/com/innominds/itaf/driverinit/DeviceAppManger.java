package com.innominds.itaf.driverinit;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.innominds.itaf.frameworkengine.ConfigTestFixtures;
import com.innominds.itaf.frameworkengine.Constants;
import com.innominds.itaf.frameworkengine.WaitForPage;

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
	 */
	@SuppressWarnings("rawtypes")
	public AppiumDriver lauchDeviceDriver(String env) throws MalformedURLException {
		if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
			setAndroidCapabilities();
			driver = new AndroidDriver(new URL(ConfigTestFixtures.getAppiumServerUrl()), capabilities);
		} else if (env.equalsIgnoreCase("ios")) {
			setIosCapabilities();
			driver = new IOSDriver(new URL(ConfigTestFixtures.getAppiumServerUrl()), capabilities);
		}
		WaitForPage.implicitWait();
		System.out.println(" Hilti App Opened Successfully------------");
		return driver;
	}

	/**
	 * Checks if is app installed.
	 *
	 * @return true, if is app installed
	 */
	public boolean isAppInstalled() {
		Boolean app = driver.isAppInstalled(ConfigTestFixtures.getBundleId());
		System.out.println("======isAppInstalled :   " + app);
		return app;
	}

	/**
	 * Sets the ios capabilities.
	 */
	public void setIosCapabilities() {
		File ipaFilePath = new File(Constants.IPAs_DRIVER_PATH, Constants.IPAs_INSTALL_FILE);
		System.out.println("App Path:====>" + ipaFilePath.getAbsolutePath());
		capabilities.setCapability("platformName", ConfigTestFixtures.getPlatformName());
		capabilities.setCapability("platform", ConfigTestFixtures.getPlatform());
		capabilities.setCapability("platformVersion", ConfigTestFixtures.getPlatformVersion());
		capabilities.setCapability("deviceName", ConfigTestFixtures.getDeviceName());
		capabilities.setCapability("bundleId", ConfigTestFixtures.getBundleId());
		capabilities.setCapability("udid", ConfigTestFixtures.getUdid());
		capabilities.setCapability("--full-reset", false);
		capabilities.setCapability("--ipa", ipaFilePath.getAbsolutePath());

		System.out.println(" DeviceName: " + ConfigTestFixtures.getDeviceName() + "\n platformName: "
				+ ConfigTestFixtures.getPlatformName() + "\n platformVersion: "
				+ ConfigTestFixtures.getPlatformVersion() + "\n appPackage: " + ConfigTestFixtures.getAppPackage()
				+ "\n appActivity: " + ConfigTestFixtures.getAppActivity());
	}

	/**
	 * Sets the android capabilities.
	 */
	public void setAndroidCapabilities() {
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", ConfigTestFixtures.getDeviceName());
		capabilities.setCapability("platformName", ConfigTestFixtures.getPlatformName());
		capabilities.setCapability("platformVersion", ConfigTestFixtures.getPlatformVersion());
		capabilities.setCapability("appPackage", ConfigTestFixtures.getAppPackage());
		capabilities.setCapability("appActivity", ConfigTestFixtures.getAppActivity());
		System.out.println(" DeviceName: " + ConfigTestFixtures.getDeviceName() + "\n platformName: "
				+ ConfigTestFixtures.getPlatformName() + "\n platformVersion: "
				+ ConfigTestFixtures.getPlatformVersion() + "\n appPackage: " + ConfigTestFixtures.getAppPackage()
				+ "\n appActivity: " + ConfigTestFixtures.getAppActivity());
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
	public void uninstallApp(String env) throws IOException, InterruptedException {
		if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
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
		} else if (env.equalsIgnoreCase("ios")) {
			driver.removeApp(ConfigTestFixtures.getBundleId());
		}
		WaitForPage.waitFor5Sec();
		System.out.println("Hilti-App Uninstalled  SUCCESSFULLY-------------");
	}
}
