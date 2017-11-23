package com.Automation.Dinh.HybridApp.excelReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_reader {
	public String[][] getExcelData(String excellocation, String sheetName){
		try {
			String dataSet[][] = null;
			
			FileInputStream file = new FileInputStream(new File(excellocation));
			
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int totalRow=sheet.getLastRowNum()+1;
			
			int totalColumn=sheet.getRow(0).getLastCellNum();
			
			dataSet = new String [totalRow-1][totalColumn];
			
			Iterator<Row> rowIterator = sheet.iterator();
			
			int i = 0;
			int t = 0;
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if(i++ !=0) {
					int k =t;
					t++;
					Iterator<Cell> cellIterator = row.cellIterator();
					int j = 0;
					while(cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch(cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							dataSet[k][j++] = cell.getStringCellValue();
							System.out.println(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							dataSet[k][j++]=cell.getStringCellValue();
							System.out.println(cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							dataSet[k][j++]=cell.getStringCellValue();
							System.out.println(cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_FORMULA:
							dataSet[k][j++]=cell.getStringCellValue();
							System.out.println(cell.getStringCellValue());
							break;
						}						
						
					}
				}
				file.close();
				return dataSet;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
