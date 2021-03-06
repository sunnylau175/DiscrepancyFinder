/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discrepancyfinder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author sunny
 */




public class Settings {
    
    private final ArrayList<Mapping> signMap = new ArrayList<>();
    private final ArrayList<MappingSetting> mappingSettings = new ArrayList<>();
    private final ArrayList<FileSetting> fileSettings = new ArrayList<>();
    private Connection dbConnection;
    
    
    public void connectDB() throws ClassNotFoundException, SQLException {
    
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        
        dbConnection = DriverManager.getConnection("jdbc:hsqldb:file:discrepencyfinder", "SA", "");
        
        checkAndCreateTable();
            
    }
    
    public void closeDB() throws SQLException {
        
        dbConnection.close();
        
    }
    
        
    private void checkAndCreateTable() {
        
        try {
        	Statement s = dbConnection.createStatement();
        	
            DatabaseMetaData dbm = dbConnection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "FILE_SETTINGS", null);
            
            if (!tables.next()) {
                //HSQL Statement
                //System.out.println("Creating table FILE_SETTINGS...");
                s.executeQuery("CREATE TABLE FILE_SETTINGS ("
                        + "SID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY, "
                        + "SETTING_NAME VARCHAR(255), "
                        + "ROW_LABEL INTEGER, "
                        + "COL_LABEL INTEGER, "
                        + "START_ROW INTEGER, "
                        + "START_COL INTEGER, "
                        + "END_ROW INTEGER, "
                        + "END_COL INTEGER, "
                        + "ROW_LABEL_DOWN_PADDING INTEGER, "
                        + "ROW_LABEL_UP_PADDING INTEGER, "
                        + "DOWN_PADDING INTEGER, "
                        + "UP_PADDING INTEGER)");
            }
            
            dbm = dbConnection.getMetaData();
            tables = dbm.getTables(null, null, "SIGN_MAPPINGS", null);
            
            if (!tables.next()) {
                //System.out.println("Creating table SIGN_MAPPINGS...");
                s.executeQuery("CREATE TABLE SIGN_MAPPINGS ("
                        + "SID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY, "
                		+ "MID INTEGER NOT NULL, "
                        + "FILE1_SIGN VARCHAR(255), "
                        + "FILE2_SIGN VARCHAR(255))");
            
            }
            
            dbm = dbConnection.getMetaData();
            tables = dbm.getTables(null, null, "MAPPING_SETTINGS", null);
            
            if (!tables.next()) {
                //System.out.println("Creating table MAPPING_SETTINGS...");
                s.executeQuery("CREATE TABLE MAPPING_SETTINGS ("
                        + "MID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY, "
                        + "MAPPING_NAME VARCHAR(255))");
            
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
        }
        
        
    
    }
    
    private void updateSettings() {
        fileSettings.clear();
        
        try {
            try (Statement s = dbConnection.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM FILE_SETTINGS")) {
                
                while (rs.next()) {
                    fileSettings.add(new FileSetting(rs.getInt("SID"), rs.getString("SETTING_NAME"), rs.getInt("ROW_LABEL"), rs.getInt("COL_LABEL"), rs.getInt("START_ROW"), rs.getInt("START_COL"), rs.getInt("END_ROW"), rs.getInt("END_COL"), rs.getInt("ROW_LABEL_DOWN_PADDING"), rs.getInt("ROW_LABEL_UP_PADDING"), rs.getInt("DOWN_PADDING"), rs.getInt("UP_PADDING")));
                    
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
        
    }
    
    public ArrayList<FileSetting> getSettings() {
        
        updateSettings();
        
        return fileSettings;
    
    }
    
    public void saveSettings() {
        
        try {
            
            
            try (Statement s = dbConnection.createStatement()) {
                for (FileSetting f: fileSettings) {
                                    	
                    s.executeQuery("UPDATE FILE_SETTINGS SET "
                            + "SETTING_NAME = '" + f.settingName + "', "
                            + "ROW_LABEL = " + f.rowLabel + ", "
                            + "COL_LABEL = " + f.colLabel + ", "
                            + "START_ROW = " + f.startRow + ", "
                            + "START_COL = " + f.startCol + ", "
                            + "END_ROW = " + f.endRow + ", "
                            + "END_COL = " + f.endCol + ", "
                            + "ROW_LABEL_DOWN_PADDING = " + f.rowLabelDownPadding + ", "
                            + "ROW_LABEL_UP_PADDING = " + f.rowLabelUpPadding + ", "
                            + "DOWN_PADDING = " + f.downPadding + ", "
                            + "UP_PADDING = " + f.upPadding + " WHERE SID = " + f.settingID);
                    
                    
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
        
        
    
    }
    
    public void deleteSettings(int settingID) {
    	try {
			Statement s = dbConnection.createStatement();
			
			//System.out.println("Deleting Record from FILE_SETTINGS...");
			
			s.executeQuery("DELETE FROM FILE_SETTINGS WHERE SID = " + settingID);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
			
		}
    	
    	updateSettings();
    	
    }
    
    public void deleteMappingSettings(int mappingID) {
    	try {
			Statement s = dbConnection.createStatement();
			
			//System.out.println("Deleting Record from MAPPING_SETTINGS...");
			
			s.executeQuery("DELETE FROM MAPPING_SETTINGS WHERE MID = " + mappingID);
			
			//System.out.println("Deleting Record from SIGN_MAPPINGS...");
			
			s.executeQuery("DELETE FROM SIGN_MAPPINGS WHERE MID = " + mappingID);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
			
		}
    	
    	updateMappingSettings();
    	updateSignMap();
    	
    }
    
    public void deleteSignMap(int signID) {
    	try {
			Statement s = dbConnection.createStatement();
						
			//System.out.println("Deleting Record from SIGN_MAPPINGS...");
			
			s.executeQuery("DELETE FROM SIGN_MAPPINGS WHERE SID = " + signID);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
			
		}
    	
    	updateSignMap();
    	
    }
    
    public void addSettings(FileSetting setting) {
        try {
            Statement s = dbConnection.createStatement();
            //System.out.println("Inserting Record...");
            s.executeQuery("INSERT INTO FILE_SETTINGS ("
                    + "SETTING_NAME, "
                    + "ROW_LABEL, "
                    + "COL_LABEL, "
                    + "START_ROW, "
                    + "START_COL, "
                    + "END_ROW, "
                    + "END_COL, "
                    + "ROW_LABEL_DOWN_PADDING, "
                    + "ROW_LABEL_UP_PADDING, "
                    + "DOWN_PADDING, "
                    + "UP_PADDING) VALUES ('"
                    + setting.settingName + "', "
                    + setting.rowLabel + ", "
                    + setting.colLabel + ", "
                    + setting.startRow + ", "
                    + setting.startCol + ", "
                    + setting.endRow + ", "
                    + setting.endCol + ", "
                    + setting.rowLabelDownPadding + ", "
                    + setting.rowLabelUpPadding + ", "
                    + setting.downPadding + ", "
                    + setting.upPadding + ")");
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
        
        updateSettings();
    
    }
    
    private void updateSignMap() {
        
        signMap.clear();
        
        try {
           
            try (Statement s = dbConnection.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM SIGN_MAPPINGS")) {
                
                while (rs.next()) {
                    signMap.add(new Mapping(rs.getInt("SID"), rs.getInt("MID"), rs.getString("FILE1_SIGN"), rs.getString("FILE2_SIGN")));
                    
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
        
        
    }
    
    private void updateMappingSettings() {
    	
    	mappingSettings.clear();
        
        try {
           
            try (Statement s = dbConnection.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM MAPPING_SETTINGS")) {
                
                while (rs.next()) {
                    mappingSettings.add(new MappingSetting(rs.getInt("MID"), rs.getString("MAPPING_NAME")));
                    
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
    	
    	
    }
    
    public ArrayList<MappingSetting> getMappingSettings() {
        
        updateMappingSettings();
        
        return mappingSettings;
        
    }
    
    
    public ArrayList<Mapping> getMapping() {
        
        updateSignMap();
        
        return signMap;
        
    }
    
    
    public int addMapping(Mapping mapping) {
        
    	int signID = -1;
    	
        try {
            try (Statement s = dbConnection.createStatement()) {
                s.executeUpdate("INSERT INTO SIGN_MAPPINGS (MID, FILE1_SIGN, FILE2_SIGN) VALUES (" + mapping.mappingID + ", '" + mapping.file1Sign + "', '" + mapping.file2Sign + "')", Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = s.getGeneratedKeys();
                
                while (rs.next())
                	signID = rs.getInt("SID");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
        
        updateSignMap();
        
        return signID;
    
    }
    
    
    /* Return generated mappingID */
    public int addMappingSetting(MappingSetting mappingSetting) {
    	
    	int mappingID = -1;
    	
    	try {
            try (Statement s = dbConnection.createStatement()) {
            	s.executeUpdate("INSERT INTO MAPPING_SETTINGS (MID, MAPPING_NAME) VALUES (NULL, '" + mappingSetting.mappingName + "')", Statement.RETURN_GENERATED_KEYS);
            	ResultSet rs = s.getGeneratedKeys();
            	
            	while (rs.next())
            		mappingID = rs.getInt("MID");
            	
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
        
        updateMappingSettings();
        
        return mappingID;
    	
    }
    
    public void saveMapping() {
   
        try {
            
            try (Statement s = dbConnection.createStatement()) {
                for (Mapping m: signMap) {
                    
                    s.executeQuery("UPDATE SIGN_MAPPINGS SET MID = " + m.mappingID + ", FILE1_SIGN = '" + m.file1Sign + "', FILE2_SIGN = '" + m.file2Sign + "' WHERE SID = " + m.signID);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
    
    }
    
    public void saveMappingSettings() {
    	
    	try {
            
            try (Statement s = dbConnection.createStatement()) {
                for (MappingSetting ms: mappingSettings) {
                    
                    s.executeQuery("UPDATE MAPPING_SETTINGS SET MAPPING_NAME = '" + ms.mappingName + "' WHERE MID = " + ms.mappingID);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(AlertType.WARNING, "Database SQL Error.").showAndWait();
            
        }
    	
    }
    
}
