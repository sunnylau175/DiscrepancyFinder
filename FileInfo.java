/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discrepancyfinder;

import java.io.File;

/**
 *
 * @author sunny
 */
public class FileInfo {
    
    public File excelFile;
    public String sheetName;
    public FileSetting setting;

    public FileInfo() {
        this.setting = new FileSetting();
    }
    
    
    
}
