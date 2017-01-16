package com.innominds.itaf.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.innominds.itaf.frameworkengine.CommonUtils;
import com.innominds.itaf.frameworkengine.Constants;

import oracle.jdbc.pool.OracleDataSource;

/*
 * Reading, writing, manupulating of Database
 * 
 * @author Chaya Venkateswarlu
 */
public class DBUtils {
	
	PropertyFileUtils dbConfig;
	
	public DBUtils() {
		
		dbConfig = new PropertyFileUtils(CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.DB_PROPERTIES_FILE));

	}
	
	
	/*
	 * Connection url for SQL
	 */
	
	private String connSQLUrl()
	{
		String conUrl = null;
		try
		{
			String sqlConnDriver = dbConfig.getDataFromPropertyFile("sql_driver");
			String sqlConnInstance = dbConfig.getDataFromPropertyFile("sql_host")+":"+dbConfig.getDataFromPropertyFile("sql_port")+":"+dbConfig.getDataFromPropertyFile("sql_sid");

			conUrl = sqlConnDriver+":@"+sqlConnInstance;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to frame Connection URL for SQL "+e.getMessage());
		}
		return conUrl;
	}
	
	
	/*
	 * Connection url for MYSQL
	 */
	
	private String connMySQLUrl()
	{
		String conUrl = null;
		try
		{
			String mySqlConnDriver = dbConfig.getDataFromPropertyFile("mysql_driver");
			String mySqlConnInstance = dbConfig.getDataFromPropertyFile("mysql_host")+":"+dbConfig.getDataFromPropertyFile("mysql_port")+"/"+dbConfig.getDataFromPropertyFile("mysql_sid");

			conUrl = mySqlConnDriver+"://"+mySqlConnInstance;
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to frame Connection URL for MySQL "+e.getMessage());
		}
		return conUrl;
	}
	
	
	
	/*
	 * Read data from SQL Oracle Database
	 * 
	 * @param sqlQuery
	 */
	
	public ResultSet readSQLData(String sqlQuery)
	{
		ResultSet rs = null;
		try
		{
			String sqlConnUserName = dbConfig.getDataFromPropertyFile("sql_UserName");
			String sqlConnPassword = dbConfig.getDataFromPropertyFile("sql_Password");

			OracleDataSource ods = new OracleDataSource();
			ods.setURL(connSQLUrl());
			Connection sqlCon = ods.getConnection(sqlConnUserName, sqlConnPassword);
			sqlCon.setAutoCommit(false);
			
			Statement stmt = sqlCon.createStatement();
			rs = stmt.executeQuery(sqlQuery);
					
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to get data for SQL "+e.getMessage());
		}
		return rs;
	}

	
	/*
	 * Read data from MySQL Database
	 * 
	 * 
	 * @param MySqlQuery
	 */
	
	public ResultSet readMySQLData(String mySqlQuery)
	{
		ResultSet rs = null;
		try
		{
			String mySqlConnUserName = dbConfig.getDataFromPropertyFile("mysql_UserName");
			String mySqlConnPassword = dbConfig.getDataFromPropertyFile("mysql_Password");

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(connMySQLUrl(), mySqlConnUserName, mySqlConnPassword);
			connection.setAutoCommit(false);
			
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(mySqlQuery);
					
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to get data for MySQL "+e.getMessage());
		}
		return rs;
	}
	
	
	
	/*
	 * Write data from SQL Oracle Database
	 * 
	 * @param sqlQuery
	 */
	
	public int writeSQLData(String sqlQuery)
	{
		int rsUpdate;
		try
		{
			String sqlConnUserName = dbConfig.getDataFromPropertyFile("sql_UserName");
			String sqlConnPassword = dbConfig.getDataFromPropertyFile("sql_Password");

			OracleDataSource ods = new OracleDataSource();
			ods.setURL(connSQLUrl());
			Connection sqlCon = ods.getConnection(sqlConnUserName, sqlConnPassword);
			sqlCon.setAutoCommit(false);
			
			Statement stmt = sqlCon.createStatement();
			rsUpdate = stmt.executeUpdate(sqlQuery);
					
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to update data for SQL "+e.getMessage());
		}
		return rsUpdate;
	}

	
	/*
	 * Write data from MySQL Oracle Database
	 * 
	 * @param sqlQuery
	 */
	
	public int writeMySQLData(String mySqlQuery)
	{
		int rsUpdate;
		try
		{
			String mySqlConnUserName = dbConfig.getDataFromPropertyFile("mysql_UserName");
			String mySqlConnPassword = dbConfig.getDataFromPropertyFile("mysql_Password");

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(connMySQLUrl(), mySqlConnUserName, mySqlConnPassword);
			connection.setAutoCommit(false);
			
			Statement stmt = connection.createStatement();
			rsUpdate = stmt.executeUpdate(mySqlQuery);
					
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to update data for MySQL "+e.getMessage());
		}

		return rsUpdate;
	}
	
	
	
	
	

}
