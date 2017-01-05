package com.innominds.itaf.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.innominds.itaf.frameworkengine.Constants;


/*
 * Property File Reader and Writer API's
 * 
 * @author Chaya Venkateswarlu
 * 
 */

public class PropertyFileUtils {
	
	static Properties properties = new Properties();
	public FileInputStream fis;
	protected String filePath;
	
	
	
	public PropertyFileUtils(String path) 
	{
	 try {
		 	this.filePath = path;
			String configFilePath = new File(path).getAbsolutePath();
			fis = new FileInputStream(configFilePath);
			properties.load(fis);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load Properties file: "+e.getMessage());
		}
		
		
	}
	
	/**
	 * Gets the property.
	 * 
	 * @param key
	 *            the key
	 * @return the property
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getDataFromPropertyFile(String key) 
	{  
		
		String value = "";
		try
		{
			value = properties.getProperty(key);
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to read Properties file data "+e.getMessage());
		}
		return value;
	}

	

	
	/**
	 * Sets the property.
	 * 
	 * @param propertyName
	 *            the property name
	 * @param propertyValue
	 *            the property value
	 */
	public static void setProperty(String path, String propertyName, String propertyValue) {
		InputStream projectStream = null;
		FileOutputStream outStream = null;
		try {
			java.util.Properties projectProperties = new java.util.Properties();
			projectStream = new FileInputStream(new File(path));
			projectProperties.load(projectStream);

			outStream = new FileOutputStream(path);
			projectProperties.setProperty(propertyName, propertyValue);
			projectProperties.store(outStream, null);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (projectStream != null) {
				try {
					projectStream.close();
				} catch (IOException e) {
					System.out.println(e.getStackTrace());
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					System.out.println(e.getStackTrace());
				}
			}
		}
	}
	

	@SuppressWarnings("unused")
	public static String getPropValuesFromConfig(String propertyKey) throws FileNotFoundException
	{
		Map<String, String> propMap = new HashMap<String, String>();
		String propFileName = null;
	
		propFileName = "email.properties";
		
		String configFilePath = Constants.ENVIRONMENT_PROPERTIES_PATH + propFileName;
		FileInputStream fileInput = new FileInputStream(configFilePath);

		if (configFilePath != null) 
		{
			try {
					properties.load(fileInput);
				} catch (Exception e) 
				{
					throw new FileNotFoundException("property file '"+ propFileName + "' not found in the classpath");
				}
			}
		return (String) properties.get(propertyKey);
	}

	
	@SuppressWarnings("unused")
	public static String getPropValuesFromConfig(String propertyKey, String environment) throws FileNotFoundException
	{
		Map<String, String> propMap = new HashMap<String, String>();
		String propFileName = null;
		if (environment.trim().equalsIgnoreCase("Testing"))
			propFileName = "web.properties";
		if (environment.trim().equalsIgnoreCase("DB") ||environment.trim().equalsIgnoreCase("database") || environment.trim().equalsIgnoreCase("Database"))
			propFileName = "DBUtils.properties";
		
		String configFilePath = Constants.ENVIRONMENT_PROPERTIES_PATH + propFileName;
		FileInputStream fileInput = new FileInputStream(configFilePath);

		if (configFilePath != null) 
		{
			try {
					properties.load(fileInput);
				} catch (Exception e) 
				{
					throw new FileNotFoundException("property file '"+ propFileName + "' not found in the classpath");
				}
			}
		return (String) properties.get(propertyKey);
	}
	
	/**
	 * Gets the prop values from config.
	 *
	 * @param propertyKey
	 *            the property key
	 * @param environment
	 *            the environment
	 * @return the prop values from config
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unused")
	public static String getPropValuesFromConfig(String propertyKey, String webEnv, String mobileEnv)
			throws FileNotFoundException, IOException {
		String propFileName = null;
		if (mobileEnv.trim().equalsIgnoreCase("android"))
			propFileName = "android.properties";
		if (mobileEnv.trim().equalsIgnoreCase("atid"))
			propFileName = "atidreader.properties";
		if (mobileEnv.trim().equalsIgnoreCase("ios"))
			propFileName = "ios.properties";

		String configFilePath = Constants.ENVIRONMENT_PROPERTIES_PATH + propFileName;
		FileInputStream fileInput = new FileInputStream(configFilePath);

		if (configFilePath != null) {
			try {
				properties.load(fileInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return (String) properties.get(propertyKey);
	}



}
