/**
 * 
 */
package com.innominds.itaf.frameworkengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.innominds.itaf.driverinit.DriverManager;

import io.appium.java_client.AppiumDriver;

/**
 * The Class with wrapper function for webdriver api's
 * 
 * @author paggrawal, Chaya Venkateswarlu, Praveen Gaddam and Kiran Kumar Cherukuri
 */
public class PageActionUtils extends DriverManager {

	
	
	


/*	public PageActionUtils() throws FileNotFoundException, IOException {
		super();
		
	}
*/
	public WebDriverWait wait;
	public static final int DEFAULT_TIME_OUT_IN_SECONDS = 60;
	public static Logger logger = Logger.getLogger(PageActionUtils.class.getName());
	AppiumDriver<WebElement> appiumDriver;
	
	


	/**
	 * Gets the web element by.
	 * @param a
	 * @return the web element by
	 */
	public By getWebElementBy(Map<String, String> a) {
		By locator = null;
		try {
			if (!a.get("PropertyType").isEmpty()) {
				if (a.get("PropertyType").toLowerCase().equals("id")) {
					locator = By.id(a.get("PropertyValue"));
				} else if (a.get("PropertyType").toLowerCase().equals("name")) {
					locator = By.name(a.get("PropertyValue"));
				} else if (a.get("PropertyType").toLowerCase().equals("xpath")) {
					locator = By.xpath(a.get("PropertyValue"));
				} else if (a.get("PropertyType").toLowerCase()
						.equals("linktext")) {
					locator = By.linkText(a.get("PropertyValue"));
				} else if (a.get("PropertyType").toLowerCase().equals("css")) {
					locator = By.cssSelector(a.get("PropertyValue"));
				}

			} else {
				logger.info("Property type is empty");
			}

		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return locator;

	}

	/**
	 * Gets the web element.
	 * 
	 * @param map2
	 * @param keyWord
	 * @return the web element
	 */
	public By getElementLocator(Map<String, Map<String, String>> map2,
			String keyWord) {
		Map<String, String> map3 = map2.get(keyWord);
		By locator = getWebElementBy(map3);
		return locator;
	}

	/**
	 * Gets the web element.
	 * @param a
	 * @return the web element
	 */
	public WebElement getWebElement(Map<String, String> a) {
		WebElement we = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIME_OUT_IN_SECONDS);
			if (!a.get("PropertyType").isEmpty()) {
				if (a.get("PropertyType").toLowerCase().equals("id")) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By
							.id(a.get("PropertyValue"))));
					we = driver.findElement(By.id(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("name")) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By
							.name(a.get("PropertyValue"))));
					we = driver.findElement(By.name(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("xpath")) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath(a.get("PropertyValue"))));
					we = driver.findElement(By.xpath(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase()
						.equals("linktext")) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By
							.linkText(a.get("PropertyValue"))));
					we = driver
							.findElement(By.linkText(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("css")) {
					we = driver.findElement(By.cssSelector(a
							.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("class")) {
					we = driver
							.findElement(By.className(a.get("PropertyValue")));
				}
			} else {
				logger.info("Property type is empty");
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return we;
	}

	/**
	 * Gets the web element.
	 * 
	 * @param map2
	 * @param keyWord
	 * @return the web element
	 */
	public WebElement getWebElement(Map<String, Map<String, String>> map2,
			String keyWord) {
		Map<String, String> map3 = map2.get(keyWord);
		WebElement we = getWebElement(map3);
		return we;
	}
	
	/**
	 * Gets the web elements.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return the web elements
	 */
	public List<WebElement> getWebElements(Map<String, Map<String, String>> map2, String keyWord) {
		Map<String, String> map3 = map2.get(keyWord);
		List<WebElement> list = getWebElements(map3);
		return list;
	}

	/**
	 * Gets the web elements.
	 * @param a
	 * @return the web elements
	 */
	public List<WebElement> getWebElements(Map<String, String> a) {
		List<WebElement> list = null;
		try {
			if (!a.get("PropertyType").isEmpty()) {
				if (a.get("PropertyType").toLowerCase().equals("id")) {
					Thread.sleep(500);
					list = driver.findElements(By.id(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("name")) {
					Thread.sleep(500);
					list = driver.findElements(By.name(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("xpath")) {
					Thread.sleep(500);
					list = driver.findElements(By.xpath(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("linktext")) {
					Thread.sleep(500);
					list = driver.findElements(By.linkText(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("css")) {
					Thread.sleep(500);
					list = driver.findElements(By.cssSelector(a.get("PropertyValue")));
				}else if (a.get("PropertyType").toLowerCase().equals("class")) {
					Thread.sleep(500);
					list = driver.findElements(By.className(a.get("PropertyValue")));
				}
			} else {
				logger.info("Property type is empty");
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return list;
	}
	

	/**
	 * Switch to popup window.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void switchToWindowHandle() throws FileNotFoundException,
			IOException, InterruptedException {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String currentWindow : allWindows) 
			{
				driver.switchTo().window(currentWindow);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Failed: to switch to new Window "+ex.getMessage());
		}
	}

	/**
	 * Maximize window.
	 * @throws Exception
	 */
	public void maxmizeWindow() throws Exception 
	{
		try
		{
			driver.manage().window().maximize();
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to maximize window "+e.getMessage());
		}
	}

	/**
	 * Gets the dynamic xpath by locator.
	 * @param value
	 * @param columnname
	 * @return the dynamic xpath by locator
	 */

	public By getDynamicXpathByLocator(String value, String columnname) {
		By locator = null;
		if (columnname.equalsIgnoreCase("Status")) {
			locator = By.cssSelector("[aria-label='Column File Name, Value "+ value + "']+td");
		} else if (columnname.equalsIgnoreCase("Remarks")) {
			locator = By.cssSelector("[aria-label='Column File Name, Value "+ value + "']+td+td");
		} else if (columnname.equalsIgnoreCase("Report")) {
			locator = By.cssSelector("[aria-label='Column File Name, Value "+ value + "']+td+td+td>div");
		} else if (columnname.equalsIgnoreCase("Name")) {
			locator = By.cssSelector("td[aria-label='Column " + columnname	+ ", Value " + value + "']");
		} else if (columnname.equalsIgnoreCase("ID")) {
			locator = By.cssSelector("td[aria-label='Column " + columnname	+ ", Value " + value + "']");
		}
			return locator;
	}

	/**
	 * Gets the web element.
	 * @param a
	 * @return the web element
	 */
	public WebElement getMobileWebElement(Map<String, String> a) {
		WebElement we = null;
		try {
			if (!a.get("PropertyType").isEmpty()) {
				if (a.get("PropertyType").toLowerCase().equals("id")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.id(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("name")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.name(a.get("PropertyValue")));

				} else if (a.get("PropertyType").toLowerCase().equals("xpath")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.xpath(a.get("PropertyValue")));

				} else if (a.get("PropertyType").toLowerCase().equals("linktext")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.linkText(a.get("PropertyValue")));

				} else if (a.get("PropertyType").toLowerCase().equals("css")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.cssSelector(a.get("PropertyValue")));
				}
				
			} else {
				logger.info("Property type is empty");
			}

		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return we;
	}

	/**
	 * Gets the web element.
	 * @param map2
	 * @param keyWord
	 * @return the web element
	 */
	public WebElement getMobileWebElement(Map<String, Map<String, String>> map2, String keyWord) {
		Map<String, String> map3 = map2.get(keyWord);
		WebElement we = getMobileWebElement(map3);
		return we;
	}
	
	/**
	 * Gets the web elements.
	 * @param map2
	 * @param keyWord
	 * @return the web elements
	 */
	public List<WebElement> getMobileElements(Map<String, Map<String, String>> map2, String keyWord) {
		Map<String, String> map3 = map2.get(keyWord);
		List<WebElement> list = getMobileElements(map3);
		return list;
	}

	/**
	 * Gets the web elements.
	 * @param a
	 * @return the web elements
	 */
	public List<WebElement> getMobileElements(Map<String, String> a) {
		List<WebElement> list = null;
		try {
			if (!a.get("PropertyType").isEmpty()) {
				if (a.get("PropertyType").toLowerCase().equals("id")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.id(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("name")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.name(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("xpath")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.xpath(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("linktext")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.linkText(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("css")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.cssSelector(a.get("PropertyValue")));
				}else if (a.get("PropertyType").toLowerCase().equals("class")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.className(a.get("PropertyValue")));
				}
			} else {
				logger.info("Property type is empty");
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return list;
	}
	
	

	/**
	 * Waits until page title is displayed.
	 * 
	 * @param driver
	 * @param title
	 * @throws InterruptedException
	 */
	public boolean assertPageTitle(String title) throws InterruptedException 
	{
		String pageTitle = null;
		while (StringUtils.isBlank(pageTitle) || !StringUtils.equals(pageTitle, title)) 
		{
			try {
				pageTitle = driver.getTitle();
				logger.info("Page title is : " + pageTitle);
			} catch (Exception ex) {
				logger.error("Page title exception", ex);
			}
			if (StringUtils.isNotBlank(pageTitle) && StringUtils.equals(pageTitle, title))
			driver.wait();
		}
		return true;
	}

	/**
	 * Get element using partialLinkText
	 * 
	 * @param innerText
	 * @return
	 */
	public WebElement findElementByPartialLinkText(String innerText) {
		return driver.findElement(By.partialLinkText(innerText));
	}

	/**
	 * Search for elements under given tag name, return first match
	 * @param current
	 * @param elementTagName
	 * @return
	 */
	public WebElement getElementByTagName(WebElement parentElement, String elementTagName)
			throws ElementNotVisibleException {
		List<WebElement> tmpList = parentElement.findElements(By.tagName(elementTagName));
		Iterator<WebElement> itr = tmpList.iterator();
		while (itr.hasNext()) {
			WebElement tmpEle = itr.next();
			if (tmpEle.isDisplayed()) {
				return tmpEle;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param we
	 * @throws NoSuchElementException
	 */
	public void doubleClickElement(WebElement we) throws NoSuchElementException {
		new Actions(driver).doubleClick(we).perform();
		logger.info("Double clicked on webelement");

	}

	/**
	 * 
	 * @param we
	 * @throws NoSuchElementException
	 */
	public void mouseHover(WebElement we) throws NoSuchElementException {
		new Actions(driver).moveToElement(we).build().perform();
		logger.info("Hover mouse on webelement");

	}

	/**
	 * 
	 * @param element
	 */
	public void rightClick(WebElement element) throws NoSuchElementException {
		new Actions(driver).contextClick(element).build().perform();
		logger.info("Sucessfully Right clicked on the element");
	}

	/**
	 * Select option of <select> by value
	 * @param element
	 * @param value
	 */
	public void selectByValue(WebElement element, String value)
			throws NoSuchElementException, IllegalArgumentException {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/** Check checkbox identified by locator
	 * @param element */
	public void checkCheckbox(WebElement element) {
		if (!element.isSelected()) {
			element.click();
		}
	}
	
	
	public void click(WebElement element) throws IllegalArgumentException 
	{
		try
		{
			element.click();
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to click WebElement "+e.getMessage());
		}

	}
	
	public void enterText(WebElement element, String text)  
	{
		try
		{
			element.clear();
			element.sendKeys(text.trim());
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to enter input text "+e.getMessage());
		}

	}
	
	public void clearText(WebElement element)  
	{
		try
		{
			element.clear();
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to enter input text "+e.getMessage());
		}

	}


	public void waitAlertAndClose(boolean isAccept) throws IllegalArgumentException 
	{
		try
		{
			new WebDriverWait(driver, DEFAULT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.alertIsPresent());
			Alert al = driver.switchTo().alert();
			if (isAccept) {
				al.accept();
			} else {
				al.dismiss();
			}
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to get alert "+e.getMessage());
		}

	}
		
	
	public String getTextFromJSAlert(boolean isAccept)
	{
		String alertText = "";
		try
		{
			new WebDriverWait(driver, DEFAULT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.alertIsPresent());
			Alert al = driver.switchTo().alert();
			alertText = al.getText().trim();
			if (isAccept) {
				al.accept();
			} else {
				al.dismiss();
			}
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to get text from Alert "+e.getMessage());
		}
	return alertText;	
	}

	
	public String getValue(WebElement element) {
		return element.getAttribute("value");
	}

	/**
	 * Returns true if element is visible, else false.
	 * @param element
	 * @param msg
	 * @return
	 * @throws ElementNotVisibleException
	 */
	public boolean isElementVisible(WebElement element, String msg) throws ElementNotVisibleException {
		logger.info(msg);
		return element.isDisplayed();

	}

	/**
	 * Validate string text in object contains a substring.
	 * @param element
	 * @param subString
	 * @throws ElementNotVisibleException
	 * @throws NoSuchElementException
	 */
	public void verifyTextPresent(WebElement element, String subString)
			throws ElementNotVisibleException, NoSuchElementException {
		String elementText;
		if (element.getText() != null) {
			elementText = element.getText();
		} else {
			elementText = element.getTagName();
		}
		if (!elementText.contains(subString)) {
			Assert.fail("Text not present in the message string");
			logger.info(subString + "Not present");
		}
	}


	public void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
		try {
			if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) {
				new Actions(driver).dragAndDrop(sourceElement, destinationElement).build().perform();
			} else {
				logger.info("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			logger.info("Element with " + sourceElement + "or" + destinationElement
					+ "is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.info("Element " + sourceElement + "or" + destinationElement + " was not found " + e.getStackTrace());
		} catch (Exception e) {
			logger.info("Error occurred while performing drag and drop operation " + e.getStackTrace());
		}
	}
	
	public static File captureScreenshot(String filename) {
		File file = null;
		try {
			String filePath = Constants.SCREENSHOT_FOLDER_PATH + File.separator
					+ filename;
			File folder = new File(filePath);
			if (!folder.exists()) {
				boolean result = false;
				try {
					folder.mkdir();
					result = true;
				} catch (Exception e) {
					// e.printStackTrace();
				}
				if (result) {
					System.out.println("Folder with name " + filename
							+ " created");
				}
			}
			file = new File(folder + "//" + filename + ".jpg");
			if (file.exists()) {
				file.delete();
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(file.toString()));
				System.out
						.println("Snapahot has been taken for the Failed test case");
			} else {
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(file.toString()));
				System.out
						.println("Snapahot has been taken for the Failed test case");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;

	}
	
	
	public void jsClick(WebElement myWebElement)
	{
		try
		{
			
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", myWebElement);
		}
		catch(Exception e)
		{
				throw new RuntimeException("Failed: to click the webelement "+e.getMessage());
		}
	}
	
	public void jsRefreshBrowser() 
	{
		try
		{
			((JavascriptExecutor) driver).executeScript("history.go(0)");
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed: to Refresh the browser "+e.getMessage());
		}
	}
	public String jsInnerText()
	{
		try
		{
			String sText =  ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;").toString();
			return sText;
		}
		catch(Exception e)
		{
				throw new RuntimeException("Failed: to get the inner text of the page"+e.getMessage());
		}
	}
	public void jsScrollWindow()
	{
		try
		{
			//Vertical scroll - down by 150  pixels
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,150)");
		}
		catch(Exception e)
		{
				throw new RuntimeException("Failed: to scroll the window "+e.getMessage());
		}
	}
	
	
	public void jsDragAndDrop(WebElement dragElementFrom, int xTo, int yTo)
	{
		try
		{
			//To drag and drop element by 100 pixel offset In horizontal direction X
			((JavascriptExecutor) driver).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
				    "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
				    dragElementFrom,xTo,yTo);
		}
		catch(Exception e)
		{
				throw new RuntimeException("Failed: to drag and drop the element "+e.getMessage());
		}
	}
	
	public String jsGetTitle() 
	{
		try
		{
			String sText =  ((JavascriptExecutor) driver).executeScript("return document.title;").toString();
			return sText;
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed: to get the title of the web page "+e.getMessage());
		}
	}
	
	public void jsEnableElement(WebElement element) 
	{
		try
		{
			String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
			((JavascriptExecutor) driver).executeScript(js, element);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed: to get the title of the web page "+e.getMessage());
		}
	}
	
	
	public void jsGenerateAlertPopup(String alert_message)
	{
		try
		{
			if(alert_message.isEmpty())
			{
				alert_message="Test alert";
			}
			((JavascriptExecutor) driver).executeScript("alert(alert_message);");
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed: to generate the alert popup "+e.getMessage());
		}
	}
	
	public void navigateForward()
	{
		try
		{
			driver.navigate().forward();
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to navigate forward to the next page "+e.getMessage());
		}
	}
	
	public void navigateBack()
	{
		try
		{
			driver.navigate().back();
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to navigate back to the previous page "+e.getMessage());
		}
	}
	
	public void refreshBrowser()
	{
		try
		{
			driver.navigate().refresh();
			
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to refresh to the page "+e.getMessage());
		}
	}
	
	
		
	public boolean checkElementIsDisplayed(WebElement element, String UserNameID)
	{
		try
		{
			boolean status = element.isDisplayed();
			return status;
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed: to check if the element is displayed or not"+e.getMessage());
		}
	}
	
	public boolean checkElementIsSelected(WebElement element)
	{
		try
		{
			boolean status = element.isSelected();
			return status;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to check if the element is selected or not"+e.getMessage());
		}
	}
	
	public void submitForm(WebElement element)
	{
		try
		{
			element.submit();
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to submit the form"+e.getMessage());
		}
		
	}
	
	public String getInnerText(WebElement element)
	{
		try
		{
			String linkText = element.getText();
			return linkText;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to get inner text of the page"+e.getMessage());
		}
	}
	

	public Select selectElementByVisibleText(WebElement element,String Text)
	{
		try
		{
			Select oSelect = new Select(element);
			oSelect.selectByVisibleText(Text);
			return oSelect ;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to select the element by visible text"+e.getMessage());
		}
	}
	
	public Select selectElementByIndex(WebElement element, int indexid)
	{
	try{
		Select oSelect = new Select(element);
		oSelect.selectByIndex(indexid);
		return oSelect ;
	}
	catch(Exception e)
	{
	throw new RuntimeException("Failed: to select the element by Index"+e.getMessage());
	}
	}
	
	public Select selectElementByValue(WebElement element, String value)
	{
		try{
			Select oSelect = new Select(element);
			oSelect.selectByValue(value);
			return oSelect ;
		}
		catch(Exception e)
		{
		throw new RuntimeException("Failed: to select the element by Value"+e.getMessage());
		}
	}
	
	@SuppressWarnings("null")
	public List<String> getAllOptions(WebElement element)
	{
		List<String> options = null;
		try
		{
			Select oSelect = new Select(element);
			List <WebElement> elementCount = oSelect.getOptions();
			int iSize = elementCount.size();
		 
			for(int i =0; i>iSize ; i++)
			{
				options.add(elementCount.get(i).getText());
			}
		}
		catch(Exception e)
		{
		throw new RuntimeException("Failed: to get the options for the selected tag"+e.getMessage());
		}
	return options;
	}
	
	public String getSelectedOption(WebElement element)
	{
		String selOption = "";
		try
		{
			Select oSelect = new Select(element);
			selOption = oSelect.getFirstSelectedOption().getText().trim();
		}
		catch(Exception e)
		{
		throw new RuntimeException("Failed: to get selected option for the selected tag"+e.getMessage());
		}
	return selOption;
	}
	
	@SuppressWarnings("null")
	public List<String> getSelectedOptions(WebElement element)
	{
		List<String> secOptions = null;
		try
		{
			Select oSelect = new Select(element);
			List <WebElement> elementCount = oSelect.getAllSelectedOptions();
			int iSize = elementCount.size();
		 
			for(int i =0; i>iSize ; i++)
			{
				secOptions.add(elementCount.get(i).getText());
			}
		}
		catch(Exception e)
		{
		throw new RuntimeException("Failed: to get selected options for the selected tag"+e.getMessage());
		}
	return secOptions;
	}



}