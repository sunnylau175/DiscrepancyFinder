package discrepancyfinder;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ModifyFileSettingsDialogController {
	
	@FXML
	private TextField modifySettingName;
	@FXML
	private TextField modifyRowLabel;
	@FXML
	private TextField modifyColumnLabel;
	@FXML
	private TextField modifyStartRow;
	@FXML
	private TextField modifyStartColumn;
	@FXML
	private TextField modifyEndRow;
	@FXML
	private TextField modifyEndColumn;
	@FXML
	private TextField modifyRowLabelDownPadding;
	@FXML
	private TextField modifyRowLabelUpPadding;
	@FXML
	private TextField modifyDownPadding;
	@FXML
	private TextField modifyUpPadding;
	@FXML
	private Button modifyOK;
	@FXML
	private Button modifyCancel;
	
	private MainApp mainApp;
	
	private ArrayList<FileSetting> fileSettings;
	private int currentSelection;
	
	public void setFileSettings(ArrayList<FileSetting> fileSettings) {
		this.fileSettings = fileSettings;
		
	}
	
	public void setCurrentSelection(int index) {
		currentSelection = index;
		
	}
	
	public TextField getTextFieldSettingName() {
		return modifySettingName;
	}
	
	public TextField getTextFieldRowLabel() {
		return modifyRowLabel;
	}
	
	public TextField getTextFieldColumnLabel() {
		return modifyColumnLabel;
	}
	
	public TextField getTextFieldStartRow() {
		return modifyStartRow;
	}
	
	public TextField getTextFieldStartColumn() {
		return modifyStartColumn;
	}
	
	public TextField getTextFieldEndRow() {
		return modifyEndRow;
	}
	
	public TextField getTextFieldEndColumn() {
		return modifyEndColumn;
	}
	
	public TextField getTextFieldRowLabelDownPadding() {
		return modifyRowLabelDownPadding;
	}
	
	public TextField getTextFieldRowLabelUpPadding() {
		return modifyRowLabelUpPadding;
	}
	
	public TextField getTextFieldDownPadding() {
		return modifyDownPadding;
	}
	
	public TextField getTextFieldUpPadding() {
		return modifyUpPadding;
	}
	
	
	
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	@FXML
    private void handleModifyAction(ActionEvent event) {
		
		try {
			
			int rowLabel = Integer.parseInt(modifyRowLabel.getText());
			int colLabel = Integer.parseInt(modifyColumnLabel.getText());
			int startRow = Integer.parseInt(modifyStartRow.getText());
			int startCol = Integer.parseInt(modifyStartColumn.getText());
			int endRow = Integer.parseInt(modifyEndRow.getText());
			int endCol = Integer.parseInt(modifyEndColumn.getText());
			int rowLabelDownPadding = Integer.parseInt(modifyRowLabelDownPadding.getText());
			int rowLabelUpPadding = Integer.parseInt(modifyRowLabelUpPadding.getText());
			int downPadding = Integer.parseInt(modifyDownPadding.getText());
			int upPadding = Integer.parseInt(modifyUpPadding.getText());
			
			mainApp.getDatabase().connectDB();
			
			fileSettings.get(currentSelection).settingName = modifySettingName.getText();
			fileSettings.get(currentSelection).rowLabel = rowLabel;
			fileSettings.get(currentSelection).colLabel = colLabel;
			fileSettings.get(currentSelection).startRow = startRow;
			fileSettings.get(currentSelection).startCol = startCol;
			fileSettings.get(currentSelection).endRow = endRow;
			fileSettings.get(currentSelection).endCol = endCol;
			fileSettings.get(currentSelection).rowLabelDownPadding = rowLabelDownPadding; 
			fileSettings.get(currentSelection).rowLabelUpPadding = rowLabelUpPadding;
			fileSettings.get(currentSelection).downPadding = downPadding;
			fileSettings.get(currentSelection).upPadding = upPadding;
						
			mainApp.getDatabase().saveSettings();
			
			mainApp.getDatabase().closeDB();
			
			Node source = (Node) event.getSource();
			Stage window = (Stage) source.getScene().getWindow();
			window.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		} catch (NumberFormatException e) {
			new Alert(AlertType.WARNING, "Field cannot be converted to integer.").showAndWait();
			
		}
				
	}
	
	@FXML
    private void handleCanelAction(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage window = (Stage) source.getScene().getWindow();
		window.close();
		
	}
	
	
	

	@FXML
	private void initialize() {
		
		
		
	}
	
	
}
