package com.innominds.itaf.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.innominds.itaf.frameworkengine.Constants;

/**
 * 
 * This class reads data from config.properties to create Issue in JIRA
 * 
 * @author Pradeeep Reddy K
 *
 */
public class JsonParameters {

	Properties props = new Properties();

	public JsonParameters() {
		this.props = openPropertyFile((new File(Constants.ENVIRONMENT_PROPERTIES_PATH
				+ Constants.JIRA_FILE_NAME)));
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
		this.projectID = props.getProperty("projectId");
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType() {
		this.issueType = props.getProperty("issueType");
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter() {
		this.reporter = props.getProperty("reporter");
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee() {
		this.assignee = props.getProperty("assignee");
	}

	public String getURL() {
		return URL;
	}

	public void setURL() {
		this.URL = props.getProperty("URL");
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName() {
		this.userName = props.getProperty("username");
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord() {
		this.passWord = props.getProperty("password");
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
