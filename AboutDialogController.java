package discrepancyfinder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutDialogController {
	
	@FXML
	private Button btnClose;
	
	
	
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
