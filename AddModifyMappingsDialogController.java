package discrepancyfinder;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddModifyMappingsDialogController {
	
	@FXML
	Button btnAddSign1;
	
	@FXML
	Button btnAddSign2;
	
	@FXML
	TextField textMappingName;
	
	@FXML
	TextField textSign1;
	
	@FXML
	TextField textSign2;
	
	@FXML
	Button btnAdd;
	
	@FXML
	Button btnSave;
	
	@FXML
	Button btnDelete;
	
	@FXML
	Button btnClose;
	
	@FXML
	ListView<String> lstMappings;
	
	private MainApp mainApp;
	
	private ArrayList<Mapping> mappings;
	private ArrayList<Mapping> currentMappings = new ArrayList<Mapping>();
	
	private int mappingID = -1;
	
	private boolean mappingSettingsAdded = false;
		
	public void setMappings(ArrayList<Mapping> mappings) {
		this.mappings = mappings;
		
	}
		
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	public void setModifyObjective(int mappingID) {
		this.mappingID = mappingID;
		
		mappingSettingsAdded = true;
		
	}
	
	public ListView<String> getMappingsListView() {
		return lstMappings;
		
	}
	
	public TextField getMappingNameTextField() {
		return textMappingName;
		
	}
	
	public TextField getSign1TextField() {
		return textSign1;
		
	}
	
	public TextField getSign2TextField() {
		return textSign2;
		
	}
	
	public ArrayList<Mapping> getCurrentMappings() {
		return currentMappings;
		
	}
	
	private void refreshMappings() {
		
		//System.out.println("refreshing mappings list...");
		
		mainApp.getMappingsObservableList().clear();
		
		currentMappings.clear();
				
		for (Mapping m: mappings) {
			if (m.mappingID == mappingID) {
				mainApp.getMappingsObservableList().add(m.file1Sign + " = " + m.file2Sign);
				currentMappings.add(m);
			}
			
		}
		
		lstMappings.setItems(mainApp.getMappingsObservableList());
		
		/*for (String s : mainApp.getMappingsObservableList()) {
			System.out.println(s);
			
		}*/
			
				
	}
	
	@FXML
	private void handleDeleteAction(ActionEvent event) {
				
		if (!mappingSettingsAdded)
			return;
		
		int selectedIndex = lstMappings.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex != -1) {
			
			try {
				mainApp.getDatabase().connectDB();
				mainApp.getDatabase().deleteSignMap(currentMappings.get(selectedIndex).signID);
				mainApp.getDatabase().closeDB();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
				
			}
						
			refreshMappings();
			
		}
		
		
	}
	
	@FXML
	private void handleSaveAction(ActionEvent event) {
		
		if (textMappingName.getText().trim().isEmpty() || textSign1.getText().trim().isEmpty() || textSign2.getText().trim().isEmpty())
			return;
		
		if (!mappingSettingsAdded)
			return;
		
		int selectedIndex = lstMappings.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex != -1) {
			currentMappings.get(selectedIndex).file1Sign = textSign1.getText();
			currentMappings.get(selectedIndex).file2Sign = textSign2.getText();
			
			try {
				mainApp.getDatabase().connectDB();
				mainApp.getDatabase().saveMapping();
				mainApp.getDatabase().closeDB();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
				
			}
						
			refreshMappings();
			
		}
		
		
	}
	
	@FXML
    private void handleAddAction(ActionEvent event) {
		
		if (textMappingName.getText().trim().isEmpty() || textSign1.getText().trim().isEmpty() || textSign2.getText().trim().isEmpty())
			return;
		
		try {
			
			mainApp.getDatabase().connectDB();
		
			if (!mappingSettingsAdded) {
				mappingID = mainApp.getDatabase().addMappingSetting(new MappingSetting(0, textMappingName.getText().trim()));			
				mappingSettingsAdded = true;
				//System.out.println("Inserted Mapping Setting, MID = " + mappingID);
			}
			else {
				for (MappingSetting ms: mainApp.getDatabase().getMappingSettings())
					if (ms.mappingID == mappingID)
						ms.mappingName = textMappingName.getText();
				
				mainApp.getDatabase().saveMappingSettings();
				
			}
		
			if (mappingID != -1)
				mainApp.getDatabase().addMapping(new Mapping(0, mappingID, textSign1.getText().trim(), textSign2.getText().trim()));
			
			
			mappings = mainApp.getDatabase().getMapping();
			
			mainApp.getDatabase().closeDB();
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		}
		
		refreshMappings();
		
		
	}
	
	@FXML
    private void handleCloseAction(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage window = (Stage) source.getScene().getWindow();
		window.close();
		
	}
	
	
	@FXML
	private void initialize() {
		
			
	}
	
	

}
