package com.innominds.itaf.frameworkengine;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.innominds.itaf.utils.PropertyFileUtils;

/**
 * The Class ConfigTestFixtures.
 * 
 * @author Praveen Gaddam
 */
public class ConfigTestFixtures {
	
	//For Mobile
	public static String Environment;
	private static String emailId1;
	private static String emailpwd1;
	private static String innomindsEmailHost;
	private static String innomindsEmailPort;
	private static String innomindsEmailId;
	private static String innomindsEmailPassword;
	private static String innomindsSendEmailHost;
	private static String gmailSendEmailHost;
	private static String sendEmailFrom;
	private static String sendEmailPwd;
	private static String sendEmailTo;
	private static String sendEmailPort;
	
	private static String deviceName;
	private static String platformName;
	private static String platform;
	private static String platformVersion;
	private static String bundleId;
	private static String udid;
	private static String appiumServerUrl;
	private static String objectRepoFile;
	private static String appPackage;
	private static String appActivity;
	private static String userName;
	private static String passWord;
	
	// For Web Application
	private static String webUsername;
	private static String webPassword;
	private static String webAppUrl;
	private static String gmailEmailHost;
	private static String deviceModel;
	
	private static String browser;
	private static String dataRow;
	
	public static String getDeviceModel() {
		return deviceModel;
	}

	public static String getBrowser() {
		return browser;
	}

	public static String getDataRow() {
		return dataRow;
	}

	public static void setBrowser(String browser) {
		ConfigTestFixtures.browser = browser;
	}

	public static void getDataRow(String datarow) {
		ConfigTestFixtures.dataRow = datarow;
	}
	public static void setDeviceModel(String deviceModel) {
		ConfigTestFixtures.deviceModel = deviceModel;
	}

	public static String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public static void setDeviceManufacturer(String deviceManufacturer) {
		ConfigTestFixtures.deviceManufacturer = deviceManufacturer;
	}

	private static String deviceManufacturer;

	public static String getWebUsername() {
		return webUsername;
	}

	public static void setWebUsername(String webUsername) {
		ConfigTestFixtures.webUsername = webUsername;
	}

	public static String getWebPassword() {
		return webPassword;
	}

	public static void setWebPassword(String webPassword) {
		ConfigTestFixtures.webPassword = webPassword;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		ConfigTestFixtures.userName = userName;
	}

	public static String getPassWord() {
		return passWord;
	}

	public static void setPassWord(String passWord) {
		ConfigTestFixtures.passWord = passWord;
	}


	public static String getDeviceName() {
		return deviceName;
	}

	public static void setDeviceName(String deviceName) {
		ConfigTestFixtures.deviceName = deviceName;
	}

	public static String getPlatformName() {
		return platformName;
	}

	public static void setPlatformName(String platformName) {
		ConfigTestFixtures.platformName = platformName;
	}

	public static String getPlatform() {
		return platform;
	}

	public static void setPlatform(String platform) {
		ConfigTestFixtures.platform = platform;
	}

	public static String getPlatformVersion() {
		return platformVersion;
	}

	public static void setPlatformVersion(String platformVersion) {
		ConfigTestFixtures.platformVersion = platformVersion;
	}

	public static String getBundleId() {
		return bundleId;
	}

	public static void setBundleId(String bundleId) {
		ConfigTestFixtures.bundleId = bundleId;
	}

	public static String getUdid() {
		return udid;
	}

	public static void setUdid(String udid) {
		ConfigTestFixtures.udid = udid;
	}

	public static String getAppiumServerUrl() {
		return appiumServerUrl;
	}

	public static void setAppiumServerUrl(String appiumServerUrl) {
		ConfigTestFixtures.appiumServerUrl = appiumServerUrl;
	}

	public static String getObjectRepoFile() {
		return objectRepoFile;
	}

	public static void setObjectRepoFile(String objectRepoFile) {
		ConfigTestFixtures.objectRepoFile = objectRepoFile;
	}

	public static String getAppPackage() {
		return appPackage;
	}

	public static void setAppPackage(String appPackage) {
		ConfigTestFixtures.appPackage = appPackage;
	}

	public static String getAppActivity() {
		return appActivity;
	}

	public static void setAppActivity(String appActivity) {
		ConfigTestFixtures.appActivity = appActivity;
	}


	public static void setSendEmailPort(String sendEmailPort) {
		ConfigTestFixtures.sendEmailPort = sendEmailPort;
	}

	public static String getSendEmailPort() {
		return sendEmailPort;
	}

	public static String getInnomindsSendEmailHost() {
		return innomindsSendEmailHost;
	}

	public static void setInnomindsSendEmailHost(String innomindsSendEmailHost) {
		ConfigTestFixtures.innomindsSendEmailHost = innomindsSendEmailHost;
	}

	public static String getGmailSendEmailHost() {
		return gmailSendEmailHost;
	}

	public static void setGmailSendEmailHost(String gmailSendEmailHost) {
		ConfigTestFixtures.gmailSendEmailHost = gmailSendEmailHost;
	}

	public static String getSendEmailFrom() {
		return sendEmailFrom;
	}

	public static void setSendEmailFrom(String sendEmailFrom) {
		ConfigTestFixtures.sendEmailFrom = sendEmailFrom;
	}

	public static String getSendEmailPwd() {
		return sendEmailPwd;
	}

	public static void setSendEmailPwd(String sendEmailPwd) {
		ConfigTestFixtures.sendEmailPwd = sendEmailPwd;
	}

	public static String getSendEmailTo() {
		return sendEmailTo;
	}

	public static void setSendEmailTo(String sendEmailTo) {
		ConfigTestFixtures.sendEmailTo = sendEmailTo;
	}

	
	
	public static String getEnvironment() {
		return Environment;
	}

	
	public static void setEnvironment(String environment) {
		Environment = environment;
	}

	
	public static String getWebAppUrl() {
		return webAppUrl;
	}

	
	public static void setWebAppUrl(String webAppUrl) {
		ConfigTestFixtures.webAppUrl = webAppUrl;
	}

	public static String getEmailId1() {
		return emailId1;
	}

	public static void setEmailId1(String emailId1) {
		ConfigTestFixtures.emailId1 = emailId1;
	}

	public static String getEmailpwd1() {
		return emailpwd1;
	}

	public static void setEmailpwd1(String emailpwd1) {
		ConfigTestFixtures.emailpwd1 = emailpwd1;
	}

	public static String getInnomindsEmailHost() {
		return innomindsEmailHost;
	}

	public static void setInnomindsEmailHost(String innomindsEmailHost) {
		ConfigTestFixtures.innomindsEmailHost = innomindsEmailHost;
	}

	public static String getInnomindsEmailPort() {
		return innomindsEmailPort;
	}

	public static void setInnomindsEmailPort(String innomindsEmailPort) {
		ConfigTestFixtures.innomindsEmailPort = innomindsEmailPort;
	}

	public static String getInnomindsEmailId() {
		return innomindsEmailId;
	}

	public static void setInnomindsEmailId(String innomindsEmailId) {
		ConfigTestFixtures.innomindsEmailId = innomindsEmailId;
	}

	public static String getInnomindsEmailPassword() {
		return innomindsEmailPassword;
	}

	public static void setInnomindsEmailPassword(String innomindsEmailPassword) {
		ConfigTestFixtures.innomindsEmailPassword = innomindsEmailPassword;
	}

	public static String getGmailEmailHost() {
		return gmailEmailHost;
	}

	public static void setGmailEmailHost(String gmailEmailHost) {
		ConfigTestFixtures.gmailEmailHost = gmailEmailHost;
	}

	/**
	 * Generate config test data.
	 * 
	 * @param env
	 *            the env
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void generateEmailConfigTestData(String propKey) throws FileNotFoundException, IOException 
	{
		try
		{
//			PropertyFileUtils fis = new PropertyFileUtils(Constants.ENVIRONMENT_PROPERTIES_PATH+"email.properties");
			emailId1 = PropertyFileUtils.getPropValuesFromConfig("emailId1");
			emailpwd1 = PropertyFileUtils.getPropValuesFromConfig("emailpwd1");
	
			innomindsEmailHost = PropertyFileUtils.getPropValuesFromConfig("innominds.email.host");
			innomindsEmailPort = PropertyFileUtils.getPropValuesFromConfig("innominds.email.port");
			innomindsEmailId = PropertyFileUtils.getPropValuesFromConfig("innominds.email.id");
			innomindsEmailPassword = PropertyFileUtils.getPropValuesFromConfig("innominds.email.password");
			gmailEmailHost = PropertyFileUtils.getPropValuesFromConfig("gmail.email.host");
			gmailSendEmailHost = PropertyFileUtils.getPropValuesFromConfig("gmail.sendemail.host");
			sendEmailFrom = PropertyFileUtils.getPropValuesFromConfig("sendEmailFrom");
			sendEmailPwd = PropertyFileUtils.getPropValuesFromConfig("sendEmailPwd");
			sendEmailTo = PropertyFileUtils	.getPropValuesFromConfig("sendEmailTo");
			sendEmailPort = PropertyFileUtils.getPropValuesFromConfig("sendemail.port");
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to generate Runtime Email Configuration Test Data "+e.getMessage());
		}


	}
	
	public void geRuntimeConfigTestData(String webEnv, String mobileEnv) throws FileNotFoundException, IOException 
	{
		try
		{
			deviceName = PropertyFileUtils.getPropValuesFromConfig("deviceName", "", mobileEnv);
			deviceModel=PropertyFileUtils.getPropValuesFromConfig("deviceModel", "", mobileEnv);
			deviceManufacturer = PropertyFileUtils.getPropValuesFromConfig("deviceManufacturer", "", mobileEnv);
			platformName = PropertyFileUtils.getPropValuesFromConfig("platformName", "", mobileEnv);
			platformVersion = PropertyFileUtils.getPropValuesFromConfig("platformVersion", "", mobileEnv);
			udid = PropertyFileUtils.getPropValuesFromConfig("udid", "", mobileEnv);
			appiumServerUrl = PropertyFileUtils.getPropValuesFromConfig("appiumServerUrl", "", mobileEnv);
			appPackage = PropertyFileUtils.getPropValuesFromConfig("appPackage", "", mobileEnv);
			appActivity = PropertyFileUtils.getPropValuesFromConfig("appActivity", "", mobileEnv);
			userName = PropertyFileUtils.getPropValuesFromConfig("userName", "", mobileEnv);
			passWord = PropertyFileUtils.getPropValuesFromConfig("passWord", "", mobileEnv);
	
			webUsername = PropertyFileUtils.getPropValuesFromConfig("web.app.username", webEnv, "");
			webPassword = PropertyFileUtils.getPropValuesFromConfig("web.app.password", webEnv, "");
			webAppUrl = PropertyFileUtils.getPropValuesFromConfig("web.app.url", webEnv, "");
	
			browser = PropertyFileUtils.getPropValuesFromConfig("web.app.username", webEnv, "");
			Environment = PropertyFileUtils.getPropValuesFromConfig("web.app.username", webEnv, "");
			dataRow = PropertyFileUtils.getPropValuesFromConfig("web.app.username", webEnv, "");
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to generate Runtime Configuration Test Data "+e.getMessage());
		}
	}

	

	
}
