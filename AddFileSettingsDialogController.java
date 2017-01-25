package discrepancyfinder;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AddFileSettingsDialogController {

	@FXML
	private TextField addSettingName;
	@FXML
	private TextField addRowLabel;
	@FXML
	private TextField addColumnLabel;
	@FXML
	private TextField addStartRow;
	@FXML
	private TextField addStartColumn;
	@FXML
	private TextField addEndRow;
	@FXML
	private TextField addEndColumn;
	@FXML
	private TextField addRowLabelDownPadding;
	@FXML
	private TextField addRowLabelUpPadding;
	@FXML
	private TextField addDownPadding;
	@FXML
	private TextField addUpPadding;
	@FXML
	private Button addOK;
	@FXML
	private Button addCancel;
	
	
	
	private MainApp mainApp;
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	@FXML
    private void handleAddAction(ActionEvent event) {
		
		try {
			FileSetting fs = new FileSetting(
                0, 
                addSettingName.getText(), 
                Integer.parseInt(addRowLabel.getText()),
                Integer.parseInt(addColumnLabel.getText()),
                Integer.parseInt(addStartRow.getText()),
                Integer.parseInt(addStartColumn.getText()),
                Integer.parseInt(addEndRow.getText()),
                Integer.parseInt(addEndColumn.getText()),
                Integer.parseInt(addRowLabelDownPadding.getText()),
                Integer.parseInt(addRowLabelUpPadding.getText()),
                Integer.parseInt(addDownPadding.getText()),
                Integer.parseInt(addUpPadding.getText()));
			
			mainApp.getDatabase().connectDB();
			mainApp.getDatabase().addSettings(fs);
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
