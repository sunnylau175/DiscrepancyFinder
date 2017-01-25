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
public class Mapping {
    
	public int signID;
    public int mappingID;
    public String file1Sign, file2Sign;
    
    Mapping() {
    	signID = 0;
        mappingID = 0;
        file1Sign = null;
        file2Sign = null;
    }
    
    Mapping(int signID, int mappingID, String file1Sign, String file2Sign) {
    	this.signID = signID;
        this.mappingID = mappingID;
        this.file1Sign = file1Sign;
        this.file2Sign = file2Sign;
    }
    
}
