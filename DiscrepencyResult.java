/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discrepancyfinder;

/**
 *
 * @author sunny
 */
public class DiscrepencyResult {
    
    public String colName;
    public String rowName;
    
    public String file1Data, file2Data;
    
    DiscrepencyResult() {
        colName = null;
        rowName = null;
        file1Data = null;
        file2Data = null;
    }
    
    DiscrepencyResult(String colName, String rowName, String file1Data, String file2Data) {
        this.colName = colName;
        this.rowName = rowName;
        this.file1Data = file1Data;
        this.file2Data = file2Data;
    
    }
    
    
}
