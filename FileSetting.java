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
public class FileSetting {
    public int settingID;
    public String settingName;
    public int rowLabel; // index of column of row label (non-zero based)
    public int colLabel; // index of row of column label (non-zero based)
    public int startRow, startCol; // non-zero based
    public int endRow, endCol; // non-zero based
    public int rowLabelDownPadding, rowLabelUpPadding; // padding for row label
    public int downPadding, upPadding; // padding for data row
    
    public FileSetting() {
        settingID = 0;
        settingName = null;
        rowLabel = 0;
        colLabel = 0;
        startRow = 0;
        startCol = 0;
        endRow = 0;
        endCol = 0;
        rowLabelDownPadding = 0;
        rowLabelUpPadding = 0;
        downPadding = 0;
        upPadding = 0;
    }
    
    public FileSetting(int settingID, String settingName, 
            int rowLabel, int colLabel, 
            int startRow, int startCol, 
            int endRow, int endCol, 
            int rowLabelDownPadding, int rowLabelUpPadding, 
            int downPadding, int upPadding) {
    
        this.settingID = settingID;
        this.settingName = settingName;
        this.rowLabel = rowLabel;
        this.colLabel = colLabel;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.rowLabelDownPadding = rowLabelDownPadding;
        this.rowLabelUpPadding = rowLabelUpPadding;
        this.downPadding = downPadding;
        this.upPadding = upPadding;
    
    }
    
}
