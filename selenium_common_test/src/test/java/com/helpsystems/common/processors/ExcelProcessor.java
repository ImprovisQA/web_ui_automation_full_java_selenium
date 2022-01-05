package com.helpsystems.common.processors;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelProcessor implements AttachmentProcessor {
	
	private final String type;

	public ExcelProcessor(String type) {
		this.type = type;
	}

	@Override
	public void validatePages(InputStream is, List<Integer> pagesList) throws Exception {
		throw new Exception("Unsupported operation for Excel document");
	}

	@Override
	public void validateData(InputStream is, List<?> content) throws Exception {
		if (content != null && !content.isEmpty() && content.get(0) instanceof Pair && ((Pair<?, ?>) content.get(0)).getKey() instanceof Pair) {
			@SuppressWarnings("unchecked")
			List<Pair<Pair<Integer, Integer>, String>> contentList = new ArrayList<>((List<Pair<Pair<Integer, Integer>, String>>) content);
			Workbook wb;
			if (type.equals("xls")) {
				wb = new HSSFWorkbook(is);
			} else {
				wb = new XSSFWorkbook(is);
			}
			try {
				Sheet sheet = wb.getSheetAt(0);
				Row row;
				Cell cell;
				int rows = sheet.getPhysicalNumberOfRows(); // No of rows
				for (int i = 0; i < contentList.size(); i++) {
					Pair<Pair<Integer, Integer>, String> pair = contentList.get(i);
					Pair<Integer, Integer> index = pair.getKey();
					if (index.getLeft() <= rows) {
						row = sheet.getRow(index.getLeft() - 1);
						if (index.getRight() <= row.getLastCellNum()) {
							cell = row.getCell(index.getRight() - 1);
							String cellValue = cell.toString();
							String cellValue2 = cell.toString();
							FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
							
							try {
							
								if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									String dataFormatString = cell.getCellStyle().getDataFormatString();
									if(dataFormatString.startsWith("\\")){
										dataFormatString = dataFormatString.substring(1);
									}
									DecimalFormat format = new DecimalFormat(dataFormatString);
									cellValue = format.format(cell.getNumericCellValue());
									
									if (cellValue.startsWith("m/d/yy")) {
										cellValue = cellValue2;
									}
									if (cellValue.endsWith("%\"")) {
										cellValue = cellValue2 + "%";
									}
									
									if (cellValue.equals("General0")) {
										
										cellValue = "0";
										
									}
								}
								
								if (cell!=null) {
								    switch (evaluator.evaluateFormulaCell(cell)) {
								        case Cell.CELL_TYPE_NUMERIC:
								        	String dataFormatString = cell.getCellStyle().getDataFormatString();
											if (dataFormatString.startsWith("\\")){
												dataFormatString = dataFormatString.substring(1);
											}
											DecimalFormat format = new DecimalFormat(dataFormatString);
								        	cellValue = format.format(cell.getNumericCellValue());
								        	if (cellValue.startsWith("General")) {
								        		cellValue = cellValue.substring(7);
								        	}
								        	if (cellValue.endsWith("%\"")) {
												cellValue = cell.getNumericCellValue() + "%";
											}
								            break;
								        case Cell.CELL_TYPE_BLANK:
								            break;
								        case Cell.CELL_TYPE_ERROR:
								            System.out.println(cell.getErrorCellValue());
								            break;

								        // CELL_TYPE_FORMULA will never occur
								        case Cell.CELL_TYPE_FORMULA: 
								            break;
								    }
								}
								
								
								if (pair.getValue().equals(cellValue)) {
									contentList.remove(i);
									i--;
								}
							} catch (NumberFormatException e) {
								// ignore
							}
						}
					}
				}
				if (!contentList.isEmpty()) {
					StringBuilder errorMessage = new StringBuilder("Attachement is invalid\n");
					for (Pair<Pair<Integer, Integer>, String> pair : contentList) {
						Pair<Integer, Integer> index = pair.getKey();
						errorMessage.append("At index [").append(index.getLeft()).append(",").append(index.getRight()).append("] value '").append(pair.getValue()).append("' not found\n");
					}
					throw new Exception(errorMessage.toString());
				}
			} finally {
				if (wb != null) {
					wb.close();
				}
			}
		} else {
			throw new Exception("Unsupported operation for Excel document");
		}

	}
	
}
