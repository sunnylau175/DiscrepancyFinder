/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discrepancyfinder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.DateFormatConverter;



/** 
 *
 * @author sunny
 */
public class SearchEngine {
    
    private ArrayList<Mapping> mapping;
    private final ArrayList<DiscrepencyResult> result;
    private final ArrayList<DataModel> file1Data, file2Data;
    private ThreadController threadController;
    private boolean appendRowIndex, appendColumnIndex;
    
    
    SearchEngine() {
        mapping = new ArrayList<>();
        result = new ArrayList<>();
        file1Data = new ArrayList<>();
        file2Data = new ArrayList<>();
        threadController = null;
        appendRowIndex = false;
        appendColumnIndex = false;
        
    }
    
    public void appendIndexes(boolean appendRowIndex, boolean appendColumnIndex) {
		
		this.appendRowIndex = appendRowIndex;
		this.appendColumnIndex = appendColumnIndex;
		
	}
    
    public ArrayList<DiscrepencyResult> getResult() {
    	return result;
    	
    }
    
    public void setMapping(ArrayList<Mapping> mapData) {
    	mapping = mapData;
    }
    
    public void setThreadController(ThreadController threadController) {
    	this.threadController = threadController;
    	
    }
    
    private String changeToSpecialSign(String data) {
    	
    	switch (data) {
    		case "":
    			data = "#BLANK!";
    			break;
    			    			
    	}
    	
    	return data;
    	
    }
    
    private boolean checkMapping(String file1Data, String file2Data) {
    	
    	file1Data = changeToSpecialSign(file1Data);
    	file2Data = changeToSpecialSign(file2Data);
    	
    	for (Mapping m : mapping) {
    		if (m.file1Sign.equals(file1Data) && m.file2Sign.equals(file2Data))
    			return true;
    	}
    	
    	return false;
    }
    
    private boolean correctFileName(File excelFile) {
        
        return (FilenameUtils.getExtension(excelFile.getAbsolutePath()).equalsIgnoreCase("xlsx") ||
                FilenameUtils.getExtension(excelFile.getAbsolutePath()).equalsIgnoreCase("xlsm") ||
                FilenameUtils.getExtension(excelFile.getAbsolutePath()).equalsIgnoreCase("xls"));
    
    }
    
    private String getCellValueToString (Cell cell) {
                
        if (cell == null)
        	return "";
        
        String cellValue = null;
        
        if (cell.getCellType() != Cell.CELL_TYPE_FORMULA) {
        	DataFormatter formatter = new DataFormatter();
        	cellValue = formatter.formatCellValue(cell);
        	//System.out.println(cell.getCellStyle().getDataFormatString());
        	
        }
        else {
        	DataFormatter formatter = new DataFormatter();
        	
        	switch (cell.getCachedFormulaResultType()) {
        		case Cell.CELL_TYPE_STRING:
        			cellValue = cell.getStringCellValue();
        			break;
                
        		case Cell.CELL_TYPE_NUMERIC:
        			if (DateUtil.isCellDateFormatted(cell)) {
        				//String changed = DateFormatConverter.convert(Locale.UK, cell.getCellStyle().getDataFormatString());
        				cellValue = formatter.formatRawCellContents(cell.getNumericCellValue(), cell.getCellStyle().getDataFormat(), cell.getCellStyle().getDataFormatString());
        				//System.out.println("Detected Date Format: " + cell.getCellStyle().getDataFormatString() + ", formatIndex: " + cell.getCellStyle().getDataFormat());
        				
        			}
        			else {
        				cellValue = formatter.formatRawCellContents(cell.getNumericCellValue(), cell.getCellStyle().getDataFormat(), cell.getCellStyle().getDataFormatString());
        				//System.out.println("Detected Numberic Format: " + cell.getCellStyle().getDataFormatString());
        			}
        			
        			break;
                
        		case Cell.CELL_TYPE_BLANK:
        			cellValue = "";
        			break;
    
        		case Cell.CELL_TYPE_BOOLEAN:
        			cellValue = Boolean.toString(cell.getBooleanCellValue());
        			break;
    
        		case Cell.CELL_TYPE_ERROR:
        			FormulaError errorCode = FormulaError.forInt(cell.getErrorCellValue());
        
        			cellValue = errorCode.getString();
        			break;            
                
        	}
        	
        	
        }
        
        
        return cellValue;
    
    }
    
    private String readRowLabel(FileInfo excelFile, Sheet worksheet, int index) {
    	
        Row row = worksheet.getRow(index + excelFile.setting.rowLabelUpPadding);
        
        Cell cell = row.getCell(excelFile.setting.rowLabel - 1);
        
        return appendRowIndex ? getCellValueToString(cell) + " (" + (index + 1) + ")" : getCellValueToString(cell);
        
    }
    
    private String readColLabel(FileInfo excelFile, Sheet worksheet, int index) {
        Row row = worksheet.getRow(excelFile.setting.colLabel - 1);
        
        Cell cell = row.getCell(index);
                
        return appendColumnIndex? getCellValueToString(cell) + " (" + (index + 1) + ")" : getCellValueToString(cell);
    }
    
    private void loadExcelFile(FileInfo excelFile, ArrayList<DataModel> dataBuffer) throws IOException, InvalidFormatException {
        
        Workbook workbook;
        workbook = WorkbookFactory.create(excelFile.excelFile);
        
        Sheet worksheet = workbook.getSheet(excelFile.sheetName);
        
        int rowSize = (excelFile.setting.rowLabelDownPadding > excelFile.setting.downPadding ? excelFile.setting.rowLabelDownPadding : excelFile.setting.downPadding) + 
                (excelFile.setting.rowLabelUpPadding > excelFile.setting.upPadding ? excelFile.setting.rowLabelUpPadding : excelFile.setting.upPadding) + 1;
        
        
        threadController.updateStatus("Loading Excel File: " + excelFile.excelFile.getName());
        
        // read data from excel file to data buffer
        for (int i = 0; ((excelFile.setting.startRow - 1) + i * rowSize) + excelFile.setting.upPadding < excelFile.setting.endRow; i++) {
            
        	if (threadController.terminateThread())
        		break;
        	
            int rowIndex = ((excelFile.setting.startRow - 1) + i * rowSize) + excelFile.setting.upPadding; // data row
            
            Row row = worksheet.getRow(rowIndex);
            
            //System.out.println("rowIndex = " + rowIndex + ", rowSize = " + rowSize);
            
            for (int j = excelFile.setting.startCol - 1; j < excelFile.setting.endCol; j++) {
                
            	if (threadController.terminateThread())
            		break;
            		
            	Cell cell = row.getCell(j);
                
            	/*if (cell != null)
            		System.out.println("rowIndex = " + rowIndex + ", rowSize = " + rowSize + ", i = " + i + ", j = " + j + ", cell = " + cell);
            	else
            		System.out.println("rowIndex = " + rowIndex + ", rowSize = " + rowSize + ", i = " + i + ", j = " + j + ", cell = BLANK");
            	*/
                            	
            	dataBuffer.add(new DataModel(readRowLabel(excelFile, worksheet, rowIndex - excelFile.setting.upPadding), readColLabel(excelFile, worksheet, j), getCellValueToString(cell)));
            	
                
            }
                               
        
        }
        
        workbook.close();
            
    }
    
    
    public void searchDiscrepency(FileInfo excelFile1, FileInfo excelFile2) throws IOException, InvalidFormatException {
    
        if (!correctFileName(excelFile1.excelFile) || !correctFileName(excelFile2.excelFile))
            return;
        
        result.clear();
        file1Data.clear();
        file2Data.clear();
        
        loadExcelFile(excelFile1, file1Data);
        loadExcelFile(excelFile2, file2Data);
        
        QuickSort.qsort(file1Data);
        QuickSort.qsort(file2Data);
        
        threadController.updateStatus("Checking...");
        
        for (DataModel data: file1Data) {
            
        	if (threadController.terminateThread()) {
        		threadController.updateStatus("Terminated");
        		return;
        	}
        	
            int searchResult = BinarySearch.bsearch(file2Data, KeyPicker.getPrimaryKey(data));
            
            if (searchResult != BinarySearch.KEY_NOT_FOUND) {
                                
                    int i = searchResult;
                    boolean matched = false;
                    
                    // scan if there are any result with same row name
                    while (i < file2Data.size() && KeyPicker.getPrimaryKey(file2Data.get(i)).equals(KeyPicker.getPrimaryKey(data))) {
                        
                    	if (threadController.terminateThread()) {
                    		threadController.updateStatus("Terminated");
                    		return;
                    	}
                    	
                        if (KeyPicker.getSecondaryKey(file2Data.get(i)).equals(KeyPicker.getSecondaryKey(data))) {
                            matched = true;
                            
                            // start compare content
                            //String mappedValue = mapping.get(data.data);
                            if (!checkMapping(data.data, file2Data.get(i).data)) {
                            	
                            	// compare content if no mapping was found.
                                if (!data.data.equals(file2Data.get(i).data))
                                    result.add(new DiscrepencyResult(KeyPicker.getSecondaryKey(data), KeyPicker.getPrimaryKey(data), data.data, file2Data.get(i).data));
                                
                            }

                        }
                            
                        i++;
                        
                    }
                    
                    if (!matched) {
                        // report: file1 record was not found in file2
                    	if (!checkMapping(data.data, "#NULL!"))
                    		result.add(new DiscrepencyResult(KeyPicker.getSecondaryKey(data), KeyPicker.getPrimaryKey(data), data.data, null));
                    }
                                    
            
            }
            else {
                // report: file1 record was not found in file2
            	if (!checkMapping(data.data, "#NULL!"))
            		result.add(new DiscrepencyResult(KeyPicker.getSecondaryKey(data), KeyPicker.getPrimaryKey(data), data.data, null));
            }
            
        
        }
        
        if (threadController.terminateThread())
        	threadController.updateStatus("Terminated");
        else
        	threadController.updateStatus("Finished");
        
    }
    
    
}
