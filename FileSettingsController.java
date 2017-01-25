package discrepancyfinder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

public class FileSettingsController {
	@FXML
	Button btnAddFileSettings;
	
	@FXML
	Button btnModifyFileSettings;
	
	@FXML
	Button btnDeleteFileSettings;
	
	@FXML
	ListView<String> lstFileSettings;
	
	private Stage FileSettingsDialog;
	
	private MainApp mainApp;
	
	private Window myWindow;
	
	private ArrayList<FileSetting> fileSettings;
	
	public void setFileSettings(ArrayList<FileSetting> fileSettings) {
		this.fileSettings = fileSettings;
		
	}
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	public void setMyWindow(Window myWindow) {
		this.myWindow = myWindow;
		
	}
	
	public ListView<String> getFileSettingsListView() {
		return lstFileSettings;
		
	}
	
	private void refreshFileSettings() {
		
		//System.out.println("refreshing file settings list...");
		
		mainApp.getFileSettingsObservableList().clear();
		
		
		
		for (FileSetting fs: fileSettings) {
			mainApp.getFileSettingsObservableList().add(fs.settingName);
			
		}
				
		/*for (String s : mainApp.getFileSettingsObservableList()) {
			System.out.println(s);
			
		}*/
			
	}
	
	@FXML
    private void handleAddFileSettingsAction(ActionEvent event) {
		
		//System.out.println("Add...");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AddFileSettingsDialog.fxml"));
		
		Parent root;
				
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			
			AddFileSettingsDialogController controller = loader.getController();
			
			controller.setMainApp(mainApp);
			
			FileSettingsDialog = new Stage();
			FileSettingsDialog.setScene(scene);
			FileSettingsDialog.setTitle("Add File Settings");
			FileSettingsDialog.initOwner(myWindow);
			FileSettingsDialog.initModality(Modality.WINDOW_MODAL);
			FileSettingsDialog.resizableProperty().setValue(Boolean.FALSE);
			FileSettingsDialog.showAndWait();
			
			refreshFileSettings();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		}
				
		
		
	}
	
	@FXML
    private void handleModifyFileSettingsAction(ActionEvent event) {
		
		//System.out.println("Modify...");
		
		int selectedIndex = lstFileSettings.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex == -1)
			return;
				
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ModifyFileSettingsDialog.fxml"));
		
		Parent root;
				
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			
			ModifyFileSettingsDialogController controller = loader.getController();
			
			controller.setMainApp(mainApp);
			
			controller.setFileSettings(fileSettings);
			controller.setCurrentSelection(selectedIndex);
			
			controller.getTextFieldSettingName().setText(fileSettings.get(selectedIndex).settingName);
			controller.getTextFieldRowLabel().setText(Integer.toString(fileSettings.get(selectedIndex).rowLabel));
			controller.getTextFieldColumnLabel().setText(Integer.toString(fileSettings.get(selectedIndex).colLabel));
			controller.getTextFieldStartRow().setText(Integer.toString(fileSettings.get(selectedIndex).startRow));
			controller.getTextFieldStartColumn().setText(Integer.toString(fileSettings.get(selectedIndex).startCol));
			controller.getTextFieldEndRow().setText(Integer.toString(fileSettings.get(selectedIndex).endRow));
			controller.getTextFieldEndColumn().setText(Integer.toString(fileSettings.get(selectedIndex).endCol));
			controller.getTextFieldRowLabelDownPadding().setText(Integer.toString(fileSettings.get(selectedIndex).rowLabelDownPadding));
			controller.getTextFieldRowLabelUpPadding().setText(Integer.toString(fileSettings.get(selectedIndex).rowLabelUpPadding));
			controller.getTextFieldDownPadding().setText(Integer.toString(fileSettings.get(selectedIndex).downPadding));
			controller.getTextFieldUpPadding().setText(Integer.toString(fileSettings.get(selectedIndex).upPadding));
						
			FileSettingsDialog = new Stage();
			FileSettingsDialog.setScene(scene);
			FileSettingsDialog.setTitle("Modify File Settings");
			FileSettingsDialog.initOwner(myWindow);
			FileSettingsDialog.initModality(Modality.WINDOW_MODAL);
			FileSettingsDialog.resizableProperty().setValue(Boolean.FALSE);
			FileSettingsDialog.showAndWait();
			
			refreshFileSettings();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		}
				
		
		
	}
	
	@FXML
    private void handleDeleteFileSettingsAction(ActionEvent event) {
		
		//System.out.println("Deleting...");
		
		int selectedIndex = lstFileSettings.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex == -1)
			return;
		
		try {
			mainApp.getDatabase().connectDB();
			mainApp.getDatabase().deleteSettings(fileSettings.get(selectedIndex).settingID);
			mainApp.getDatabase().closeDB();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		}
		
		
		refreshFileSettings();
		
	}
	
	@FXML
	private void initialize() {
		
		
		
	}
	
}
