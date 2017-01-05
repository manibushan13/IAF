package com.web.tests.myspace.myrequests;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.apache.log4j.Logger;
import com.innominds.itaf.frameworkengine.Constants;
import com.innominds.itaf.frameworkengine.DataInjection;

public class ApplyWFHDP extends DataInjection {
	
	Logger logger = Logger.getLogger(ApplyWFHDP.class.getName());

	
	Hashtable<String, String> td;
	HashMap<String, Map<String, String>> or;
	HashMap<String, String> config;
	String sheetName;
	
	
	 public ApplyWFHDP(final String orPath, final String tdPath, final String configPath) 
	 {
		 
		 super(orPath, tdPath, configPath);
		 this.sheetName = "WFH";
		 this.td = _testData(sheetName, prop.getDataFromPropertyFile("DataRow"));
		 this.or = _objRepository(sheetName);
		 this.config = _configData();
	}
	
	 public void setSheetName(String sheetName)
	 {
		 this.sheetName = sheetName;
	 }

	 public String getSheetName()
	 {
		 return sheetName;
	 }

	 
	 public void setORData(HashMap<String, Map<String, String>> or)
	 {
		 this.or = or;
	 }
	 
	 public HashMap<String, Map<String, String>> getORData()
	 {
		 return or;
	 }
	 
	 public void setTDData(Hashtable<String, String> td)
	 {
		 this.td = td;
	 }
	 
	 public Hashtable<String, String> getTDData()
	 {
		 return td;
	 }
	 
	 public void setConfigData(HashMap<String, String> config)
	 {
		 this.config = config;
	 }
	 
	 public HashMap<String, String> getConfigData()
	 {
		 return config;
	 }

	 
	 
	 public static Object[][] createDP()
	 {
		 
		 String orPath = Constants.WEB_OBJECT_REPO_FILE_PATH+"Web_OR.xls";
		 String tdPath = Constants.TESTDATA_FILE_PATH+"TestData.xlsx";
		 String configPath = Constants.ENVIRONMENT_PROPERTIES_PATH+"web.properties";
		  
	 
		 
		return new Object[][]{{new  ApplyWFHDP(orPath, tdPath, configPath)}};
		 
	 }
		
	

}
