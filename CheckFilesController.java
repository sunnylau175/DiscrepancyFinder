package discrepancyfinder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CheckFilesController {
	
	@FXML
	TextField textFilePath1;
	
	@FXML
	TextField textFilePath2;
	
	@FXML
	Button btnBrowse1;
	
	@FXML
	Button btnBrowse2;
	
	@FXML
	ListView<String> listFile1Settings;
	
	@FXML
	ListView<String> listFile2Settings;
	
	@FXML
	ListView<String> listMappings;
	
	@FXML
	Button btnCheck;
	
	@FXML
	RadioButton radioUseRowLabelAsPrimaryKey;
	
	@FXML
	RadioButton radioUseColumnLabelAsPrimaryKey;
	
	@FXML
	CheckBox chkAppendRowIndex;
	
	@FXML
	CheckBox chkAppendColumnIndex;
	
	@FXML
	ToggleGroup primaryKeyOptions;
		
	private MainApp mainApp;
	
	private ArrayList<FileSetting> fileSettings;
	private ArrayList<MappingSetting> mappingSettings;
	
	private Stage SelectSheetsDialog;
	
	private Window myWindow;
	
	public void setMyWindow(Window myWindow) {
		this.myWindow = myWindow;
		
	}
	
	public void setFileSettings(ArrayList<FileSetting> fileSettings) {
		this.fileSettings = fileSettings;
		
	}
	
	public void setMappingsSettings(ArrayList<MappingSetting> mappingSettings) {
		this.mappingSettings = mappingSettings;
		
	}
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	public ListView<String> getFile1SettingsListView() {
		return listFile1Settings;
		
	}
	
	public ListView<String> getFile2SettingsListView() {
		return listFile2Settings;
		
	}
	
	public ListView<String> getMappingsListView() {
		return listMappings;
		
	}
	
	@FXML
    private void handleFile1BrowseAction(ActionEvent event) {
		
		Node source = (Node) event.getSource();
		Stage window = (Stage) source.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel File", "*.xls", "*.xlsx", "*.xlsm");
		
		fileChooser.setTitle("Open Excel File");
		fileChooser.getExtensionFilters().add(filter);
		File choseFile = fileChooser.showOpenDialog(window);
		
		if (choseFile != null) {
			textFilePath1.setText(choseFile.getAbsolutePath());
			
		}
					
	}
	
	@FXML
    private void handleFile2BrowseAction(ActionEvent event) {
		
		Node source = (Node) event.getSource();
		Stage window = (Stage) source.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel File", "*.xls", "*.xlsx", "*.xlsm");
		
		fileChooser.setTitle("Open Excel File");
		fileChooser.getExtensionFilters().add(filter);
		File choseFile = fileChooser.showOpenDialog(window);
		
		if (choseFile != null) {
			textFilePath2.setText(choseFile.getAbsolutePath());
			
		}
					
	}
	
	@FXML
    private void handleCheckAction(ActionEvent event) {
		
		if (textFilePath1.equals("") || textFilePath2.equals(""))
			return;
		
		int selectedIndexForFile1Settings = listFile1Settings.getSelectionModel().getSelectedIndex();
		int selectedIndexForFile2Settings = listFile2Settings.getSelectionModel().getSelectedIndex();
		int selectedIndexForMappings = listMappings.getSelectionModel().getSelectedIndex();
		
		if (selectedIndexForFile1Settings == -1 || selectedIndexForFile2Settings == -1 || selectedIndexForMappings == -1)
			return;
		
		File excelFile1 = new File(textFilePath1.getText());
		File excelFile2 = new File(textFilePath2.getText());
						
		Workbook workbook;
		Sheet worksheet;
		
		/*For File 1*/
		try {
			workbook = WorkbookFactory.create(excelFile1);
			
			mainApp.getFile1SheetsObservableList().clear();
			
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				worksheet = workbook.getSheetAt(i);
				mainApp.getFile1SheetsObservableList().add(worksheet.getSheetName());
				
			}
				
			workbook.close();
			
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot open excel file: " + excelFile1.getAbsolutePath()).showAndWait();
			
		}
				
		/*For File 2*/
		try {
			workbook = WorkbookFactory.create(excelFile2);
			
			mainApp.getFile2SheetsObservableList().clear();
			
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				worksheet = workbook.getSheetAt(i);
				mainApp.getFile2SheetsObservableList().add(worksheet.getSheetName());
			}
			
			workbook.close();
			
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot open excel file: " + excelFile2.getAbsolutePath()).showAndWait();
			
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("SelectSheetsDialog.fxml"));
		
		Parent root;
		
		FileInfo excelFileInfo1 = new FileInfo();
		FileInfo excelFileInfo2 = new FileInfo();
		
		excelFileInfo1.excelFile = excelFile1;
		excelFileInfo2.excelFile = excelFile2;
		
		excelFileInfo1.setting = fileSettings.get(selectedIndexForFile1Settings);
		excelFileInfo2.setting = fileSettings.get(selectedIndexForFile2Settings);
		
		try {
			root = loader.load();
			Scene scene = new Scene(root);
									
			SelectSheetsDialogController controller = loader.getController();
						
			controller.setExcelFiles(excelFileInfo1, excelFileInfo2);
			
			ListView<String> listFile1Sheets = controller.getListFile1Sheet();
			ListView<String> listFile2Sheets = controller.getListFile2Sheet();
			
			listFile1Sheets.setItems(mainApp.getFile1SheetsObservableList());
			listFile2Sheets.setItems(mainApp.getFile2SheetsObservableList());
			
			listFile1Sheets.getSelectionModel().select(0);
			listFile2Sheets.getSelectionModel().select(0);
			
			SelectSheetsDialog = new Stage();
			SelectSheetsDialog.setScene(scene);
			SelectSheetsDialog.setTitle("Select Excel Worksheet");
			SelectSheetsDialog.initOwner(myWindow);
			SelectSheetsDialog.initModality(Modality.WINDOW_MODAL);
			SelectSheetsDialog.resizableProperty().setValue(Boolean.FALSE);
			SelectSheetsDialog.showAndWait();
						
			if (controller.getButtonIdentifier() == controller.BUTTON_OK_PRESSED) {
				//System.out.println("OK pressed");
				
				if (radioUseRowLabelAsPrimaryKey.isSelected()) {
					KeyPicker.setPrimaryKeySetting(KeyPicker.ROW_AS_PRIMARY_KEY);
					//System.out.println("Set row label as primary key.");
					
				}
				else if (radioUseColumnLabelAsPrimaryKey.isSelected()) {
					KeyPicker.setPrimaryKeySetting(KeyPicker.COL_AS_PRIMARY_KEY);
					//System.out.println("Set column label as primary key.");
					
				}
								
				mainApp.getDatabase().connectDB();
				ArrayList<Mapping> mapping = mainApp.getDatabase().getMapping();
				mainApp.getDatabase().closeDB();
			
				ArrayList<Mapping> selectedMapping = new ArrayList<>();
			
				for (Mapping m : mapping)
					if (m.mappingID == mappingSettings.get(selectedIndexForMappings).mappingID)
						selectedMapping.add(m);
			
				mainApp.startCheckFiles(excelFileInfo1, excelFileInfo2, selectedMapping, chkAppendRowIndex.isSelected(), chkAppendColumnIndex.isSelected());
				
			}
			
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		}
		catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		}
		
		Node source = (Node) event.getSource();
		Stage window = (Stage) source.getScene().getWindow();
		window.close();
		
	}
	
	@FXML
	private void initialize() {
		
		
		
	}

}
