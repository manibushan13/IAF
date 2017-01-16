package com.innominds.itaf.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;

import com.innominds.itaf.driverinit.DriverManager;
import com.innominds.itaf.frameworkengine.Constants;
import com.innominds.itaf.frameworkengine.SendEmail;
import com.innominds.itaf.utils.CreateIssueInJira;
import com.innominds.itaf.utils.PropertyFileUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


/**
 * The listener interface for receiving extentReporter events. The class that is
 * interested in processing a extentReporter event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's <code>addExtentReporterListener<code> method. When the
 * extentReporter event occurs, that object's appropriate method is invoked.
 *
 * @author Chaya Venkateswarlu, Kiran Kumar Cherukuri, Pradeep Reddy
 */

public class ExtentReporterListener extends TestListenerAdapter  implements ITestListener, IReporter {

	/** The extent. */
	private ExtentReports extent;

	/** The driver manager. */
	public DriverManager driverManager;
	public String screenshotPath;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.IReporter#generateReport(java.util.List, java.util.List,
	 * java.lang.String)
	 */
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory)
	{
		
		extent = new ExtentReports(Constants.REPORTS_FILE_PATH, true);
		extent.loadConfig(new File(Constants.EXTENT_CONFIG_PATH));

		try 
		{
		
		
			if (PropertyFileUtils.getPropValuesFromConfig(Constants.ANDROID_PROPERTIES_FILE, "platformName") != null) {
				extent.addSystemInfo("Mobile Device Platform", PropertyFileUtils.getPropValuesFromConfig(Constants.ANDROID_PROPERTIES_FILE, "platformName"));
			}
			if (PropertyFileUtils.getPropValuesFromConfig(Constants.ANDROID_PROPERTIES_FILE, "deviceManufacturer") != null) {
				extent.addSystemInfo("Mobile Device Name", PropertyFileUtils.getPropValuesFromConfig(Constants.ANDROID_PROPERTIES_FILE, "deviceManufacturer"));
			}
			if (PropertyFileUtils.getPropValuesFromConfig(Constants.ANDROID_PROPERTIES_FILE, "deviceModel") != null) {
				extent.addSystemInfo("Mobile Device Model", PropertyFileUtils.getPropValuesFromConfig(Constants.ANDROID_PROPERTIES_FILE, "deviceModel"));
			}
				if (PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "Environment") != null) {
						extent.addSystemInfo("Web Environment", PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "Environment"));
						extent.addSystemInfo("IP", InetAddress.getLocalHost().toString());
						extent.addSystemInfo("Host Name", InetAddress.getLocalHost().getHostName().toString());
						
				} else {
					extent.addSystemInfo("Web Environment", "");
				}
			} catch (Exception e1) {
				throw new RuntimeException("Env not found");
			}
	
			if (DriverManager.browsername != null) {
				extent.addSystemInfo("Browser", DriverManager.browsername);
			}
			
			extent.addSystemInfo("OS", System.getProperty("os.name"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			
	
			for (ISuite suite : suites) {
				Map<String, ISuiteResult> result = suite.getResults();
	
				for (ISuiteResult r : result.values()) {
					ITestContext context = r.getTestContext();
	
					buildTestNodes(context.getPassedTests(), LogStatus.PASS);
					buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
					buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
				}
			}
	
			try {
				extent.flush();
				extent.close();
			} catch (Exception e) {
	
			}
	
			SendEmail sendEmail = new SendEmail();
			try {
				sendEmail.sendEmailReport(PropertyFileUtils.getPropValuesFromConfig(Constants.EMAIL_PROPERTIES_FILE, "mailType"));
			} catch (InterruptedException | FileNotFoundException e) {
					e.printStackTrace();
			}
	}

	/**
	 * Builds the test nodes.
	 *
	 * @param tests
	 *            the tests
	 * @param status
	 *            the status
	 */
	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				System.out.println();
				test = extent.startTest(result.getMethod().getMethodName());
				test.assignAuthor(System.getProperty("user.name"));
				if(screenshotPath != null && !screenshotPath.isEmpty())
				{
					test.addScreenCapture(screenshotPath);
				}
				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable());
				} else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
				}

				extent.endTest(test);
			}
		}
	}

	/**
	 * Gets the time.
	 *
	 * @param millis
	 *            the millis
	 * @return the time
	 */
	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	
	CreateIssueInJira issueInJira = new CreateIssueInJira();
	WebDriver driver;
	
	
	public WebDriver getDriverInstance() {
		return driver = DriverManager.getDriver();
	}

	@Override
	public void onTestStart(ITestResult result) {
	

	}

	@Override
	public void onTestSuccess(ITestResult result) {
	

	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		onTestFailure(result);
	}


	@Override
	public void onTestFailure(ITestResult result) {
		try {
			issueInJira.formJson(result.getMethod().getMethodName(), result.getThrowable().getMessage());
//			Object currentClass = result.getInstance();
//			driver = ((ExtentReporterListener) currentClass).getDriverInstance();
			driver = getDriverInstance();
			if (driver != null) {
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);

				try {
					screenshotPath = "target/custom-test-reports/"
							+ result.getTestClass().getName() + "_screenshot.png";
					FileUtils.copyFile(scrFile, new File(screenshotPath));
					Reporter.log("[Console Log] Screenshot saved in "
							+ result.getTestClass().getName() + "_screenshot.png");

				} catch (IOException ex) {
					// Log error message
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to report TestFailure " +e.getMessage());
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}


}
