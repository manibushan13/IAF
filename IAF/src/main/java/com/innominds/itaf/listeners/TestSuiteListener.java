package com.innominds.itaf.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.innominds.itaf.frameworkengine.Constants;

/**
 * 
 * @author Kiran Kumar Cherukuri, Chaya Venkateswarlu
 */


public class TestSuiteListener implements IAnnotationTransformer 
{
	
	/* (non-Javadoc)
	 * @see org.testng.IAnnotationTransformer#transform(org.testng.annotations.ITestAnnotation, java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
	 */
	@Override
	public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class testClass,
			@SuppressWarnings("rawtypes") Constructor testConstructor, Method testMethod) 
	{
    	try {
    		
    		Set<Method> webTestMethods = getTestMethods("com.web.tests.WebTESTCASES");
    		for(Method method : webTestMethods)
    		{
    			setExecutor(annotation, Constants.TESTSUITE_FILE_PATH, Constants.TESTSUITE_FILE_SHEETNAME, method.getName(), testMethod.getName());
    		}
    		
       		Set<Method> mobTestMethods = getTestMethods("com.android.tests.MobTESTCASES");
    		for(Method method : mobTestMethods)
    		{
    			setExecutor(annotation, Constants.TESTSUITE_FILE_PATH, Constants.TESTSUITE_FILE_SHEETNAME, method.getName(), testMethod.getName());
    		}
 
    	} catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }	
	
	/**
	 * Sets the executor.
	 *
	 * @param annotation
	 * @param filepath
	 * @param sheetName 
	 * @param module
	 * @param testMethod
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void setExecutor(ITestAnnotation annotation,String filepath,String sheetName, String module, String testMethod) throws IOException
	{
		Path path = Paths.get(filepath);
		String fileNamefromfullfilepath = path.getFileName().toString();
		String filepathfromfullfilepath = path.getParent().toString();
		LinkedHashMap<String, String> hmSuites = readExcel(filepathfromfullfilepath, fileNamefromfullfilepath, sheetName);
    	Iterator<Entry<String, String>> Ihm=hmSuites.entrySet().iterator();
    	LinkedHashMap<String, String> hm = null;
    	while(Ihm.hasNext())
    	{
    		Entry<String, String> keyvalues=Ihm.next();
    		if(keyvalues.getKey().equalsIgnoreCase(module) && keyvalues.getValue().equalsIgnoreCase("Yes"))
    		{
    			try 
    			{
					hm = readExcel(filepathfromfullfilepath, fileNamefromfullfilepath, module);
				} catch (IOException e) 
    			{
					e.printStackTrace();
				}
    			Set<Entry<String, String>> set = hm.entrySet();
    			Iterator<Entry<String, String>> l = set.iterator();
    			while (l.hasNext()) 
    			{
    				Entry<String, String> me = l.next();
    				String methodName = me.getKey();
    				String methodRunmode = me.getValue();
    				if (methodName.equals(testMethod)) 
    				{
    					if (methodRunmode.equalsIgnoreCase("No"))
    					{
    						annotation.setEnabled(false);
    					} else 
    					{
    						annotation.setEnabled(true);
    					}
    				}
    			}
    		}
       	}    
   	}

	/**
	 * Read excel.
	 *
	 * @param filePath the file path
	 * @param fileName the file name
	 * @param sheetName the sheet name
	 * @return the linked hash map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static LinkedHashMap<String, String> readExcel(String filePath,
			String fileName, String sheetName) throws IOException 
	{
		LinkedHashMap<String, String> lhm;
		File file = new File(filePath +File.separator+ fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook DriverWorkBook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) 
		{
			DriverWorkBook = new XSSFWorkbook(inputStream);
		}
		else if (fileExtensionName.equals(".xls")) 
		{
			DriverWorkBook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = DriverWorkBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		lhm = new LinkedHashMap<String, String>();
		for (int i = 1; i < rowCount + 1; i++) 
		{
			Row row = sheet.getRow(i);
			lhm.put(row.getCell(1).getStringCellValue(), row.getCell(2)
					.getStringCellValue());
		}
		return lhm;
	}
	
	
	public static Set<Method> getTestMethods(String packagePath)
	{
		Set<Method> testMethods = null;
		try	
		{
			Reflections reflections = new Reflections( new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(packagePath) ).setScanners(
				    	    new MethodAnnotationsScanner() ).filterInputsBy(new FilterBuilder().includePackage(packagePath)) );
			testMethods = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
			
			
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to fetch test methods from package "+e.getMessage());
		}
		return testMethods;
		
	}

}