package com.innominds.itaf.frameworkengine;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.innominds.itaf.utils.PropertyFileUtils;
import com.innominds.itaf.utils.RepositoryReader;
import com.innominds.itaf.utils.TestDataReader;

public class DataInjection {
	
	
	protected static RepositoryReader repo;
	protected static TestDataReader td;
	protected static PropertyFileUtils prop;
	protected SendEmail email;
	protected Hashtable<String, String> testData;
	protected HashMap<String, Map<String, String>> or;
	protected HashMap<String, String> configProp;
	private String tdPath;
	private String orPath;
	private String configPath;
	
	

	
	public DataInjection(String orPath, String tdPath, String configPath) 
	{
		try
		{
			this.orPath = orPath;
			this.tdPath = tdPath;
			this.configPath = configPath;
			repo = new RepositoryReader(orPath);
			td = new TestDataReader(tdPath);
			prop = new PropertyFileUtils(configPath);
		
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to inject data into Tests "+e.getMessage());
		}
		
	}
	
	
	
	public Hashtable<String, String> _testData(String tdSheetName, String dataRow)
	{
		try
		{
			String type = CommonUtils.getFileExtn(tdPath);
			if(type.contains("xls")||type.contains("xlsx"))
			{
				testData = td.getDataFromTDExcel(tdSheetName, dataRow);
			}else
			{
				testData = td.getDataFromTDConfig();
			}
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to inject data into TesData "+e.getMessage());
		}
		
		return testData;
		
	}
	
	
	public HashMap<String, Map<String, String>> _objRepository(String orSheetName)
	{
		try
		{
			String type = CommonUtils.getFileExtn(orPath);
			if(type.contains("xls")||type.contains("xlsx"))
			{
				or = repo.generateOR(orSheetName);
			}else
			{
				or = repo.getDataFromORConfig();
			}
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to inject data into ObjRepository "+e.getMessage());
		}

		
		
		return or;
		
	}
	
	
	public HashMap<String, String> _configData()
	{
		try
		{
			configProp = CommonUtils.storePropIntoMap(configPath);

		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to inject data into ConfigData "+e.getMessage());
		}

		
		
		return configProp;
		
	}


	
	
}
