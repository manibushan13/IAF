package com.innominds.itaf.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.innominds.itaf.loggers.Logs;

/**
 * This class for Excel Utils
 * 
 * @author Chaya Venkateswarlu, Kiran Kumar Cherukuri
 */

public class ExcelUtils {

	public static Logs logsObj;
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	public Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;


	
	/**
	 * Instantiates a new excel utils.
	 * 
	 * @param path
	 *            the path
	 */
	public ExcelUtils(String path) {
		try {
			this.path = path;
			this.fis = new FileInputStream(path);
			this.workbook = WorkbookFactory.create(fis);
			this.sheet = this.workbook.getSheetAt(0);
			this.fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the row count.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @return the row count
	 */
	public int getRowCount(String sheetName) {
		int index = this.workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		}
		this.sheet = this.workbook.getSheetAt(index);
		int number = this.sheet.getLastRowNum() + 1;
		return number;
	}

	/**
	 * Gets the cell data.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @param colName
	 *            the col name
	 * @param rowNum
	 *            the row num
	 * @return the cell data
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}
			int index = this.workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1) {
				return "";
			}
			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(0);
			for (int i = 0; i < this.row.getLastCellNum(); ++i) {
				if (this.row.getCell(i).getStringCellValue().trim()
						.equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1) {
				return "";
			}
			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(rowNum - 1);
			if (this.row == null)
				return "";
			this.cell = this.row.getCell(col_Num);

			if (this.cell == null) {
				return "";
			}
			if (this.cell.getCellType() == 1) {
				return this.cell.getStringCellValue();
			}

			if (this.cell.getCellType() == 2) {
				int typ = this.cell.getCachedFormulaResultType();
				if (typ == 1) {
					RichTextString formulaText = this.cell.getRichStringCellValue();
					return formulaText.getString();
				}

				double formulaValue = this.cell.getNumericCellValue();
				return Double.toString(formulaValue);
			}

			if (this.cell.getCellType() == 0) {
				String cellText = String.valueOf(this.cell
						.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(this.cell)) {
					double d = this.cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = String.valueOf(cal.get(1)).substring(2);
					cellText = cal.get(5) + "/" + cal.get(2) + 1 + "/"
							+ cellText;
				}

				return cellText;
			}
			if (this.cell.getCellType() == 3) {
				return "";
			}
			return String.valueOf(this.cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "row " + rowNum + " or column " + colName
				+ " does not exist in xls";
	}

	/**
	 * Gets the cell data.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @param colNum
	 *            the col num
	 * @param rowNum
	 *            the row num
	 * @return the cell data
	 */
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}
			int index = this.workbook.getSheetIndex(sheetName);

			if (index == -1) {
				return "";
			}

			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(rowNum - 1);
			if (this.row == null)
				return "";
			this.cell = this.row.getCell(colNum);
			if (this.cell == null) {
				return "";
			}
			if (this.cell.getCellType() == 1) {
				return this.cell.getStringCellValue();
			}

			if (this.cell.getCellType() == 2) {
				int typ = this.cell.getCachedFormulaResultType();
				if (typ == 1) {
					RichTextString formulaText = this.cell.getRichStringCellValue();
					return formulaText.getString();
				}

				double formulaValue = this.cell.getNumericCellValue();
				return Double.toString(formulaValue);
			}

			if (this.cell.getCellType() == 0) {
				String cellText = String.valueOf(this.cell
						.getNumericCellValue());

				if (HSSFDateUtil.isCellDateFormatted(this.cell)) {
					double d = this.cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = String.valueOf(cal.get(1)).substring(2);
					cellText = (cal.get(2) + 1) + "/" + cal.get(5) + "/"
							+ cellText;
				}

				return cellText;
			}
			if (this.cell.getCellType() == 3) {
				return "";
			}

			return String.valueOf(this.cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "row " + rowNum + " or column " + colNum
				+ " does not exist  in xls";
	}

	/**
	 * Gets the cell row num.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @param colName
	 *            the col name
	 * @param cellValue
	 *            the cell value
	 * @return the cell row num
	 */
	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for (int i = 2; i <= getRowCount(sheetName); ++i) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if is sheet exist.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @return true, if is sheet exist
	 */
	public boolean isSheetExist(String sheetName) {
		int index = this.workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = this.workbook.getSheetIndex(sheetName.toUpperCase());

			return (index != -1);
		}

		return true;
	}

	/**
	 * Gets the column count.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @return the column count
	 */
	public int getColumnCount(String sheetName) {
		if (!(isSheetExist(sheetName))) {
			return -1;
		}
		this.sheet = this.workbook.getSheet(sheetName);
		this.row = this.sheet.getRow(0);

		if (this.row == null) {
			return -1;
		}
		return this.row.getLastCellNum();
	}

	/**
	 * Fetch single sheet data.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @param xls
	 *            the xls
	 * @return the object[][]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object[][] fetchSingleSheetData(String sheetName,
			ExcelUtils xls) {
		if (!(xls.isSheetExist(sheetName))) {
			xls = null;
			Object[][] data = new Object[1][1];
			data[0][0] = "Sheet Not Exist";

			return data;
		}

		int rows = xls.getRowCount(sheetName);
		int cols = xls.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		Hashtable table = null;
		int index = 0;

		for (int rowNum = 2; rowNum <= rows; ++rowNum) {
			table = new Hashtable();

			for (int colNum = 0; colNum < cols; ++colNum) {
				String key = xls.getCellData(sheetName, colNum, 1);
				String value = xls.getCellData(sheetName, colNum, rowNum);
				table.put(key, value);
			}

			data[index][0] = table;
			++index;
		}

		return data;
	}

	/**
	 * Gets the rowdata from excel.
	 * 
	 * @param xls
	 *            the xls
	 * @param sheetName
	 *            the sheet name
	 * @param row
	 *            the row
	 * @return the rowdata from excel
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Hashtable<String, String> getRowdataFromExcel(ExcelUtils xls,
			String sheetName, int row) {
		int generalSheetRow = row;
		Hashtable generalSheetData = new Hashtable();
		Object[][] obj_generalSheet = fetchSingleSheetData(sheetName, xls);

		if (generalSheetRow < obj_generalSheet.length) {
			generalSheetData = (Hashtable) obj_generalSheet[generalSheetRow][0];
		}

		return generalSheetData;
	}
	
	


	
	

}
