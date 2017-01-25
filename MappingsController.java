package discrepancyfinder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MappingsController {
	
	@FXML
	Button btnAddModifyMappings;
			
	@FXML
	Button btnDeleteMappings;
	
	@FXML
	ListView<String> lstMappings;
	
	private MainApp mainApp;
	
	private Window myWindow;
	
	private Stage MappingsDialog;
	
	private ArrayList<MappingSetting> mappingSettings;
	private ArrayList<Mapping> mappings;
	
	
	public void setMappingsSettings(ArrayList<MappingSetting> mappingSettings) {
		this.mappingSettings = mappingSettings;
		
	}
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	public void setMyWindow(Window myWindow) {
		this.myWindow = myWindow;
		
	}
	
	public ListView<String> getMappingsListView() {
		return lstMappings;
		
	}
	
	private void refreshMappingSettings() {
		
		//System.out.println("refreshing mappings list...");
		
		mainApp.getMappingsSettingsObservableList().clear();
		
		
		
		for (MappingSetting ms: mappingSettings) {
			mainApp.getMappingsSettingsObservableList().add(ms.mappingName);
			
		}
				
		/*for (String s : mainApp.getMappingsSettingsObservableList()) {
			System.out.println(s);
			
		}*/
				
	}
	
	@FXML
	private void handleDeleteMappingAction(ActionEvent event) {
		
		int selectedIndex = lstMappings.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex != -1) { 
			try {
				//System.out.println("Delete...");
				
				mainApp.getDatabase().connectDB();
				mainApp.getDatabase().deleteMappingSettings(mappingSettings.get(selectedIndex).mappingID);
				
				mainApp.getDatabase().closeDB();
				
				refreshMappingSettings();
				
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
				
			}
			
			
		}
		
		
	}
	
	@FXML
    private void handleAddModifyMappingsAction(ActionEvent event) {
		
		//System.out.println("Add/Modify...");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AddModifyMappingsDialog.fxml"));
		
		Parent root;
		
		
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			
			AddModifyMappingsDialogController controller = loader.getController();
			
			controller.setMainApp(mainApp);
			
			try {
				mainApp.getDatabase().connectDB();
				mappings = mainApp.getDatabase().getMapping();
				controller.setMappings(mappings);
				mainApp.getDatabase().closeDB();
										
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
				
			}
			
			mainApp.getMappingsObservableList().clear();
			
			int selectedIndex = lstMappings.getSelectionModel().getSelectedIndex();
			
			if (selectedIndex != -1) {
				int selectedMappingID = mappingSettings.get(selectedIndex).mappingID;
				controller.setModifyObjective(selectedMappingID);
				
				controller.getMappingNameTextField().setText(mappingSettings.get(selectedIndex).mappingName);
				
				controller.getCurrentMappings().clear();
				
				for (Mapping m: mappings)
					if (m.mappingID == selectedMappingID) { 
						mainApp.getMappingsObservableList().add(m.file1Sign + " = " + m.file2Sign);
						controller.getCurrentMappings().add(m);
						
					}
				
				
								
			}
			
			controller.getMappingsListView().setItems(mainApp.getMappingsObservableList());
			
			
			controller.getMappingsListView().getSelectionModel().selectedItemProperty().addListener(
					new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
							//Pattern pattern = Pattern.compile("./s=/s.");
							//Matcher matcher = pattern.matcher(new_val);
							
							//System.out.println("Selected: " + new_val);
							/*if (matcher.find()) {
								System.out.println(matcher.group());
							}*/
							
							if (new_val != null) {
								//System.out.println("Sign 1 = " + new_val.substring(0, new_val.indexOf(" = ")));
								//System.out.println("Sign 2 = " + new_val.substring(new_val.indexOf(" = ") + 3, new_val.length()));
							
								controller.getSign1TextField().setText(new_val.substring(0, new_val.indexOf(" = ")));
								controller.getSign2TextField().setText(new_val.substring(new_val.indexOf(" = ") + 3, new_val.length()));
							}
							
						}
						
					}
					
					);
			
			MappingsDialog = new Stage();
			MappingsDialog.setScene(scene);
			MappingsDialog.setTitle("Add/Modify Mappings");
			MappingsDialog.initOwner(myWindow);
			MappingsDialog.initModality(Modality.WINDOW_MODAL);
			MappingsDialog.resizableProperty().setValue(Boolean.FALSE);
			MappingsDialog.showAndWait();
			
			refreshMappingSettings();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		}
				
		
		
	}
	
	@FXML
	private void initialize() {
		
			
	}

}
