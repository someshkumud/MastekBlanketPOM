package com.test.automation.MastekBlanketPOM.excelReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {
	public FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook workbook;
	public XSSFSheet worksheet;
	public XSSFRow row;
	public XSSFCell cell;
	public String xlFilePath;
	

	public ExcelReader(String xlFilePath) {
		try {
			this.xlFilePath=xlFilePath;
			fis=new FileInputStream(xlFilePath);
			workbook=new XSSFWorkbook(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String[][] getSheetData(String sheetName,String excelName) {	
		String dataSets[][]=null;
		try {
			worksheet=workbook.getSheet(sheetName);
			int totalRow=worksheet.getLastRowNum()+1;
			int totalCol=worksheet.getRow(0).getLastCellNum();
			dataSets=new String[totalRow-1][totalCol];

			for(int rowCount = 1;rowCount<totalRow;rowCount++){
				row=worksheet.getRow(rowCount);
				for(int colCount = 0;colCount<totalCol;colCount++){
					cell = row.getCell(colCount);	
					if(cell==null){
						dataSets[rowCount-1][colCount]= "";
					}else{
						CellType cell_type = cell.getCellTypeEnum();
						if(cell_type==CellType.STRING){
							dataSets[rowCount-1][colCount]= cell.getStringCellValue();
						}else if(cell_type==CellType.NUMERIC||cell_type==CellType.FORMULA){
							String cellValue=String.valueOf(cell.getNumericCellValue());
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								DateFormat df=new SimpleDateFormat("dd/MM/yy");
								Date date=cell.getDateCellValue();
								cellValue=df.format(date);
							}
							dataSets[rowCount-1][colCount]= cellValue;
						}else
							dataSets[rowCount-1][colCount]=String.valueOf(cell.getBooleanCellValue());
					}
				}

			}
			
			return dataSets;

		} catch (Exception e) {
			System.out.println("Exception in reading .xlsx file : "+e.getMessage());
			e.printStackTrace();
		}
		return dataSets;
	}

	
	
	

	public String getCellData(String sheetName,String colName, int rowNum) {	
		
		try {
			int col_Num = -1;
			worksheet=workbook.getSheet(sheetName);
			row=worksheet.getRow(0);
			
			for(int rowCount = 0;rowCount<row.getLastCellNum();rowCount++){
				if(row.getCell(rowCount).getStringCellValue().trim().equals(colName.trim())){
					col_Num=rowCount;
				}
			}
			row=worksheet.getRow(rowNum-1);
			cell = row.getCell(col_Num);	
			if(cell==null){
				return "";
			}else{
				CellType cell_type = cell.getCellTypeEnum();
				if(cell_type==CellType.STRING){
					return cell.getStringCellValue();
				}else if(cell_type==CellType.NUMERIC||cell_type==CellType.FORMULA){
					String cellValue=String.valueOf(cell.getNumericCellValue());
					if(HSSFDateUtil.isCellDateFormatted(cell)){
						DateFormat df=new SimpleDateFormat("dd/MM/yy");
						Date date=cell.getDateCellValue();
						cellValue=df.format(date);
					}
					return cellValue;
				}else
					return String.valueOf(cell.getBooleanCellValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Row "+rowNum+" OR Colomn "+colName+" does not exist in Excel";
		}
	}
		
	
	
	
	public String getCellData(String sheetName,int colNum, int rowNum) {
		
		try {
			worksheet=workbook.getSheet(sheetName);
			row=worksheet.getRow(rowNum);
			cell = row.getCell(colNum);		
			if(cell==null){
				return "";
			}else{
				CellType cell_type = cell.getCellTypeEnum();
				if(cell_type==CellType.STRING){
					return cell.getStringCellValue();
				}else if(cell_type==CellType.NUMERIC||cell_type==CellType.FORMULA){
					String cellValue=String.valueOf(cell.getNumericCellValue());
					if(HSSFDateUtil.isCellDateFormatted(cell)){
						DateFormat df=new SimpleDateFormat("dd/MM/yy");
						Date date=cell.getDateCellValue();
						cellValue=df.format(date);
					}
					return cellValue;
				}else
					return String.valueOf(cell.getBooleanCellValue());

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Row "+rowNum+" OR Colomn "+colNum+" does not exist in Excel";
		}
		
	}

		

	public int getRowCount(String sheetName) {
			worksheet=workbook.getSheet(sheetName);
			int number=worksheet.getLastRowNum()+1;
			return number;	
	}
	
	public int getColCount(String sheetName) {
			worksheet=workbook.getSheet(sheetName);
			row=worksheet.getRow(0);
			return row.getLastCellNum();	
	}

}
