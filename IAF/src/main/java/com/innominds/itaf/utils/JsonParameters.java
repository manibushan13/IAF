package com.innominds.itaf.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.innominds.itaf.frameworkengine.CommonUtils;
import com.innominds.itaf.frameworkengine.Constants;

/**
 * 
 * This class reads data from config.properties to create Issue in JIRA
 * 
 * @author Pradeeep Reddy K
 *
 */
public class JsonParameters {

	
	PropertyFileUtils props;;

	public JsonParameters() {
		props =  new PropertyFileUtils(CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.JIRA_PROPERTIES_FILE));
		setAssignee();
		setIssueType();
		setPassWord();
		setProjectID();
		setReporter();
		setURL();
		setUserName();
	}

	private String projectID;
	private String issueType;
	private String reporter;
	private String assignee;
	private String URL;
	private String userName;
	private String passWord;

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID() {
		this.projectID = props.getDataFromPropertyFile("projectId");
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType() {
		this.issueType = props.getDataFromPropertyFile("issueType");
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter() {
		this.reporter = props.getDataFromPropertyFile("reporter");
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee() {
		this.assignee = props.getDataFromPropertyFile("assignee");
	}

	public String getURL() {
		return URL;
	}

	public void setURL() {
		this.URL = props.getDataFromPropertyFile("URL");
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName() {
		this.userName = props.getDataFromPropertyFile("username");
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord() {
		this.passWord = props.getDataFromPropertyFile("password");
	}

	/**
	 * Reads property file
	 * @param file
	 * @return
	 */
	public static Properties openPropertyFile(File file) {
		Properties propertyFile = new Properties();
		try {
			FileInputStream fis = new FileInputStream(file);
			propertyFile.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			propertyFile = null;
		}
		return propertyFile;
	}
}
