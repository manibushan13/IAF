package com.innominds.itaf.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;

import com.innominds.itaf.frameworkengine.CommonUtils;


/*
 * Object Repository Reader (OR)
 * 
 * @author Chaya Venkatswarlu
 */

public class RepositoryReader {
	
	
	
	ExcelUtils e;
	PropertyFileUtils prop;
	File file;
	
	
	/**
	 * Instantiates a new object repo reader.
	 *
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	
	public RepositoryReader(String orPath) throws IOException
	{
		
		String type = CommonUtils.getFileExtn(orPath);
		if(type.contains("xls")||type.contains("xlsx"))
		{
			e = new ExcelUtils(orPath);
		}else
		{
			prop = new PropertyFileUtils(orPath);
		}
	}



	/**
	 * Generate or.
	 *
	 * @param orSheetName the or sheet name
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HashMap<String, Map<String, String>> generateOR(String orSheetName) 	throws IOException {
	

		// Get first/desired sheet from the workbook
		Sheet sheet = e.workbook.getSheet(orSheetName);

		int nRowCount = sheet.getLastRowNum();
		
		
		HashMap<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		for (int j = 1; j <= nRowCount; j++) {

			try {

				String cell = sheet.getRow(j).getCell(0).getStringCellValue()
						.trim();
	
				String proType = sheet.getRow(j).getCell(1)
						.getStringCellValue().trim();
				
				String ProValue = sheet.getRow(j).getCell(2)
						.getStringCellValue();
				
				Map<String, String> map2 = new HashMap<String, String>();
				
				if (cell != null) {
					if (proType != null && ProValue != null) {
						map2.put(proType, ProValue);
					} else {
						map2.put(sheet.getRow(0).getCell(1)
								.getStringCellValue(), "");
					}
						map.put(cell, map2);
				}
			} catch (Exception e) {
				System.out.println(e);
			}

		}
		return map;
	}
	
	
	/*
	 * get data from OR Config
	 * 
	 * 
	 */
	
	public HashMap<String, Map<String, String>> getDataFromORConfig() 
	{
		HashMap<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, String> innerMap = new HashMap<String, String>();
		try
		{
			 Set<Object> keys = PropertyFileUtils.properties.keySet();
			    for(Object k:keys)
			    {
			    	String[] orKey = CommonUtils.splitString( (String) k, "^");
			    	innerMap.put(orKey[1], PropertyFileUtils.properties.getProperty((String) k));
			    	map.put(orKey[0], innerMap);
			    }
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to get data from Test Data "+e.getMessage());
		}
		return map;
	}


}
