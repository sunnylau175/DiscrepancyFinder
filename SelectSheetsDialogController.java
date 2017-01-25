package discrepancyfinder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SelectSheetsDialogController {
	
	@FXML
	ListView<String> listFile1Sheet;
	
	@FXML
	ListView<String> listFile2Sheet;
	
	@FXML
	Button btnOK;
	
	@FXML
	Button btnCancel;
	
	private int buttonIdentifier;
	
	
	public final int BUTTON_OK_PRESSED = 1, BUTTON_CANCEL_PRESSED = 2;
	
	private FileInfo excelFile1, excelFile2;
	
	public ListView<String> getListFile1Sheet() {
		return listFile1Sheet;
		
	}
	
	public ListView<String> getListFile2Sheet() {
		return listFile2Sheet;
		
	}
	
	public void setExcelFiles(FileInfo excelFile1, FileInfo excelFile2) {
		this.excelFile1 = excelFile1;
		this.excelFile2 = excelFile2;
		
	}
	
	public int getButtonIdentifier() {
		return buttonIdentifier;
		
	}
	
	
	
	@FXML
    private void handleOKAction(ActionEvent event) {
				
		int selectedIndexForFile1 = listFile1Sheet.getSelectionModel().getSelectedIndex();
		int selectedIndexForFile2 = listFile2Sheet.getSelectionModel().getSelectedIndex();
		
		if (selectedIndexForFile1 == -1 || selectedIndexForFile2 == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Please select sheets.");
			
			alert.showAndWait();
			return;
		}
		
		excelFile1.sheetName = listFile1Sheet.getSelectionModel().getSelectedItem();
		excelFile2.sheetName = listFile2Sheet.getSelectionModel().getSelectedItem();
		
		buttonIdentifier = BUTTON_OK_PRESSED;
				
		Node source = (Node) event.getSource();
		Stage window = (Stage) source.getScene().getWindow();
		window.close();
		
	}
	
	
	@FXML
    private void handleCanelAction(ActionEvent event) {
		
		buttonIdentifier = BUTTON_CANCEL_PRESSED;
		
		Node source = (Node) event.getSource();
		Stage window = (Stage) source.getScene().getWindow();
		window.close();
		
	}
	
	
	@FXML
	private void initialize() {
		
		
		
	}

}
