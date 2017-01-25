package discrepancyfinder;

public class MappingSetting {

	public int mappingID;
    public String mappingName;
    
    MappingSetting() {
        mappingID = 0;
        mappingName = null;
        
    }
    
    MappingSetting(int mappingID, String mappingName) {
        this.mappingID = mappingID;
        this.mappingName = mappingName;
        
    }
	
}
