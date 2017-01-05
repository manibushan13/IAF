package com.innominds.itaf.frameworkengine;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Class WaitForPage.
 * 
 * @author Kiran Kumar Cherukuri
 */
public class WaitForPage 
{
	public static WebDriver driver;
	public static WebDriverWait driverWait;
	public static int sleepwait=3500;  // in milli secs
	public static int implicitwait=10; //in  secs
	public static int explicitwait=60; //in secs
	public static int uploadwait=9000; //in milli secs
	
	/**
	 * Instantiates a new wait for page.
	 *
	 * @param driver1 the driver1
	 */
	@SuppressWarnings("static-access")
	public WaitForPage(WebDriver driver)
	{
		this.driver=driver;
	}
	
	/**
	 * Wait for element present.
	 *
	 * @param by the by
	 * @param time the time
	 */
	public static void waitForElementPresent(By by, int time) {

		driverWait = new WebDriverWait(driver, time);
		driverWait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	/**
	 * Wait for pageto load.
	 */
	public static void waitForPagetoLoad() {
		try {
			Thread.sleep(sleepwait);
		} catch (InterruptedException e) {
		}
	}
	
	public static void waitForUpload() {
		try {
			Thread.sleep(uploadwait);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Implicit wait.
	 */
	public static void implicitWait() {
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);*/
	}
	
	/**
	 * Wait for web element.
	 *
	 * @param element the element
	 */
	public static void WaitForWebElement(WebElement element) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwait, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	

	 /**
	 * Wait for pageto load.
	 */
	public static void waitForElementtoLoad() {
		try {
			Thread.sleep(sleepwait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void waitFor5Sec() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void waitFor15Sec() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
