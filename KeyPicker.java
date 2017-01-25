package discrepancyfinder;

public class KeyPicker {
	
	public static final int ROW_AS_PRIMARY_KEY = 0;	// set row as primary sort and search key
	public static final int COL_AS_PRIMARY_KEY = 1;	// set column as primary sort and search key
	
	private static int primaryKeySetting = ROW_AS_PRIMARY_KEY;
	
	public static void setPrimaryKeySetting(int setting) {
		
		if (setting != ROW_AS_PRIMARY_KEY && setting != COL_AS_PRIMARY_KEY)
			return;
		
		primaryKeySetting = setting;
		
	}
	
	public static String getPrimaryKey(DataModel dataModel) {
		
		switch (primaryKeySetting) {
			case ROW_AS_PRIMARY_KEY:
				return dataModel.rowName;
				
			case COL_AS_PRIMARY_KEY:
				return dataModel.colName;
				
			default:
				return dataModel.rowName;
		
		}
				
	}
	
	public static String getSecondaryKey(DataModel dataModel) {
		
		switch (primaryKeySetting) {
			case ROW_AS_PRIMARY_KEY:
				return dataModel.colName;
			
			case COL_AS_PRIMARY_KEY:
				return dataModel.rowName;
			
			default:
				return dataModel.colName;
	
		}
		
	}
	
}
