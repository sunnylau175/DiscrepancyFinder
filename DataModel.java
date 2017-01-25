package discrepancyfinder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sunny
 */
public class DataModel {
    
    public String rowName, colName;
    public String data;
    
    DataModel() {
        rowName = null;
        colName = null;
        data = null;
        
    }
    
    DataModel(String rowName, String colName, /*int rowNameFormat, int colNameFormat,*/ String data) {
        this.rowName = rowName;
        this.colName = colName;
        this.data = data;
        
    }
    
    
}
