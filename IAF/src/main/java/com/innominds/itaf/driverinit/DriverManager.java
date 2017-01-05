package com.innominds.itaf.driverinit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.innominds.itaf.frameworkengine.Constants;

/**
 * The Class Driver Manager.
 * 
 * @author Praveen Gaddam and Kiran Kumar Cherukuri
 */
public class DriverManager {

	protected static String browser;
	protected static String os;
	public static WebDriver driver;
	private static Logger log;
	public static String browsername;
	private static DesiredCapabilities capabilities;

	/**
	 * Instantiates a new selenium browser.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
/*	public DriverManager() throws FileNotFoundException, IOException {

		File file = new File(Constants.REPORT_DOWNLOAD_LOCATION);
		if (file.exists()) {
			file.delete();
			file.mkdir();
		} else {
			file.mkdir();
		}
	}

*/	/**
	 * Gets the driver.
	 * 
	 * @return the driver
	 */
	public static WebDriver getDriver() {
		return driver;
	}

	/**
	 * Gets the OS version.
	 * 
	 * @return the OS version
	 */
	private static String getOSVersion() {
		System.out.println("Operating System=" + System.getProperty("os.name"));
		return System.getProperty("os.name");
	}

	/**
	 * Sets the up selenium environment.
	 * 
	 * @param browser
	 *            the new up selenium environment
	 */
	public static void setupSeleniumEnvironment(String browser) {
		DriverManager.os = getOSVersion();
		System.out.println("OS Version = " + DriverManager.os + ", os.arch = "
				+ System.getProperty("os.arch"));
		String passedBrowser = browser;
		// String passedBrowser = Property.getProperty("browser");
		System.out.println("browser to run = " + passedBrowser);
		if (passedBrowser != null) {
			DriverManager.browser = passedBrowser;
		} else {
			DriverManager.browser = "chrome";
		}
		printEnvInfo();
	}

	/**
	 * Launch browser.
	 * 
	 * @param browser
	 *            the browser
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	// checks the browser input, and defaults to IE if there are any issues.
	public static void initDriver(String browser) throws FileNotFoundException 
	{
		browsername = browser;
		setupSeleniumEnvironment(browser);
		capabilities= new DesiredCapabilities();
		if (browser.toLowerCase().equals("iexplore") || browser.toLowerCase().equals("ie")	|| browser.toLowerCase().equals("internet explorer")) 
		{
			browser = "iexplore";
			if (os.contains("Windows")) {
				if (System.getProperty("os.arch").contains("86")) 
				{
					System.setProperty("webdriver.ie.driver", Constants.IE_DRIVER_32_EXE_PATH);
				} else if (System.getProperty("os.arch").contains("64")) 
				{
					System.setProperty("webdriver.ie.driver", Constants.IE_DRIVER_64_EXE_PATH);
				}
			} else {
				System.out.println("Unexpected Operating System");

			}
			
			
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
			capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
			capabilities.setCapability("dom.successive_dialog_time_limit", 0);
			driver = new InternetExplorerDriver(capabilities);
		
		} else if (browser.toLowerCase().equals("firefox") || browser.toLowerCase().equals("ff")) 
		{
			browser = "firefox";
			if (os.contains("Windows")) {
				if (System.getProperty("os.arch").contains("86")) 
				{
					System.setProperty("webdriver.gecko.driver", Constants.FIREFOX_DRIVER_32_EXE_PATH);
				} else if (System.getProperty("os.arch").contains("64")) 
				{
					System.setProperty("webdriver.gecko.driver", Constants.FIREFOX_DRIVER_64_EXE_PATH);
				}
				
				capabilities.setCapability("dom.successive_dialog_time_limit", 0);
			}
			
			driver = new FirefoxDriver(capabilities);
		
		} else if (browser.toLowerCase().equals("chrome") || browser.toLowerCase().equals("google")	|| browser.toLowerCase().equals("google chrome") || browser.toLowerCase().equals("googlechrome")) 
		{

			browser = "googlechrome";
			if (os.contains("Windows")) 
			{
				System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_WIN_32_EXE_PATH);
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("dom.successive_dialog_time_limit", 0);
				chromePrefs.put("download.default_directory", Constants.REPORT_DOWNLOAD_LOCATION);
				ChromeOptions options = new ChromeOptions();
				HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("chrome.switches", "--disable-extensions");
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				
			} else if (os.equals("Linux") && System.getProperty("os.arch").contains("64")) 
			{
				System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_LINUX_64_EXE_PATH);
			} else if (os.equals("Linux") && !System.getProperty("os.arch").contains("86")) 
			{
				System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_LINUX_32_EXE_PATH);
			} else if (os.contains("Mac")) 
			{
				System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_MAC_32_EXE_PATH);
			} else 
			{
				System.out.println("Unexpected Operating System");

			}

			driver = new ChromeDriver(capabilities);
		}

		else {
			System.out.println(browser.toLowerCase());
			browser = "iexplore";
			driver = new InternetExplorerDriver();
		}

	}

	/**
	 * Gets the current browser type.
	 * 
	 * @return the current browser type
	 */
	public static String getCurrentBrowserType() {
		return DriverManager.browser;
	}

	/**
	 * Prints the env info.
	 */
	public static void printEnvInfo() {
		System.out.println("You have selected " + browser
				+ " as your browser. " + browser + " is being used.");
	}

	/**
	 * Launch browsern load url.
	 * 
	 * @param browser
	 *            the browser
	 * @param url
	 *            the url
	 * @throws Exception
	 *             the exception
	 */
	public static void initDriverWithURL(String browser, String url)
			throws Exception {
		initDriver(browser);
		loadURL(url);
	}

	/**
	 * Load url.
	 * 
	 * @param url
	 *            the url
	 * @throws Exception
	 *             the exception
	 */
	public static void loadURL(String url) throws Exception {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
	}

	/**
	 * Quit browser.
	 * 
	 * @return the string
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String quitBrowser() throws FileNotFoundException, IOException {
		log.debug("Closing the browser");
		try {
			driver.quit();
			driver.close();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to quit the browser "+e.getMessage());
		}
		return "PASS";

	}

	/**
	 * Close browser.
	 */
	public void closeBrowser() {
		System.out.println("Closing the browser");
		driver.close();
	}

}