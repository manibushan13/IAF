package com.innominds.itaf.utils;

import java.util.Hashtable;
import java.util.Set;

import com.innominds.itaf.frameworkengine.CommonUtils;


/*
 * Reading of TestData
 * 
 * 
 * @author Chaya Venkateswarlu
 * 
 * 
 */

public class TestDataReader {
	
	ExcelUtils e;
	PropertyFileUtils prop;
	
	
	public TestDataReader(String tdPath) 
	{
		String type = CommonUtils.getFileExtn(tdPath);
		if(type.contains("xls")||type.contains("xlsx"))
		{
			e = new ExcelUtils(tdPath);
					
		}else
		{
			prop = new PropertyFileUtils(tdPath);
		}
		
		
	}
	
	
	
	
	
	public Hashtable<String, String> getDataFromTDExcel(String sheetName, String dataRow) 
	{
		Hashtable<String, String> table = null;
		try
		{
			
			if (!(e.isSheetExist(sheetName))) 
			{
				return table;
			}
	
			int rows = e.getRowCount(sheetName);
			int cols = e.getColumnCount(sheetName);
	
			
			for (int rowNum = 0; rowNum <= rows; ++rowNum) 
			{
				table = new Hashtable<String, String>();
	
				String dr = e.getCellData(sheetName, 0, rowNum);
				if(dr.equalsIgnoreCase(dataRow))
				{
					for (int colNum = 0; colNum < cols; ++colNum) {
						String key = e.getCellData(sheetName, colNum, 1);
						String value = e.getCellData(sheetName, colNum, rowNum);
						table.put(key, value);
					}
					break;
				}
	
			}
		}catch(Exception e)
		{
			
			throw new RuntimeException("failed: to read data from Test Data Excel "+e.getMessage());
			
		}
	return table;
	}
	
	
	
	
	@SuppressWarnings("null")
	public Hashtable<String, String> getDataFromTDConfig() 
	{
		Hashtable<String, String> table = null;
		try
		{
			 Set<Object> keys = PropertyFileUtils.properties.keySet();
			    for(Object k:keys)
			    {
			    	table.put((String) k, PropertyFileUtils.properties.getProperty((String) k));
			    }
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to get data from Test Data "+e.getMessage());
		}
		return table;
	}



}
