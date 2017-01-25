package discrepancyfinder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	MenuItem menuFileSettings;
	
	@FXML
	MenuItem menuMappings;
	
	@FXML
	MenuItem menuAbout;
	
	@FXML
	MenuItem menuCheck;
	
	@FXML
	MenuItem menuClose;
		
	@FXML
	Label labelStatus;
	
	@FXML
	TextArea textAreaResult;
	
	@FXML
	ImageView imgStopThread;
	
	private Stage FileSettingsChildWindow;
	private Stage MappingsChildWindow;
	private Stage AboutChildWindow;
	private Stage CheckFilesChildWindow;
	
	
	private MainApp mainApp;
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	public Label getLabelStatus() {
		return labelStatus;
		
	}
	
	public TextArea getTextAreaResult() {
		return textAreaResult;
		
	}
	public ImageView getStopButton() {
		return imgStopThread;
		
	}
	
	@FXML
	private void handleStopThread(MouseEvent event) {
		mainApp.stopCheckFiles();
		
	}
	
	@FXML
    private void handleMenuCheckAction(ActionEvent event) {
		
		//System.out.println("menu check selected.");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("CheckFilesDialog.fxml"));
		
		Parent root;
			
		try {
						
			root = loader.load();
			Scene scene = new Scene(root);
			
			CheckFilesController controller = loader.getController();
			controller.setMainApp(mainApp);
			
			mainApp.getDatabase().connectDB();
			ArrayList<FileSetting> fileSettings = mainApp.getDatabase().getSettings();
			ArrayList<MappingSetting> mappingSettings = mainApp.getDatabase().getMappingSettings();
			mainApp.getDatabase().closeDB();
			
			controller.setFileSettings(fileSettings);
			controller.setMappingsSettings(mappingSettings);
			
			mainApp.getFileSettingsObservableList().clear();
			for (FileSetting fs: fileSettings) {
				mainApp.getFileSettingsObservableList().add(fs.settingName);
				
			}
			
			mainApp.getMappingsSettingsObservableList().clear();
			for (MappingSetting ms: mappingSettings) {
				mainApp.getMappingsSettingsObservableList().add(ms.mappingName);
				
			}
			
			controller.getFile1SettingsListView().setItems(mainApp.getFileSettingsObservableList());
			controller.getFile2SettingsListView().setItems(mainApp.getFileSettingsObservableList());
			controller.getMappingsListView().setItems(mainApp.getMappingsSettingsObservableList());
			
			controller.getFile1SettingsListView().getSelectionModel().select(0);
			controller.getFile2SettingsListView().getSelectionModel().select(0);
			controller.getMappingsListView().getSelectionModel().select(0);
						
			CheckFilesChildWindow = new Stage();
			
			controller.setMyWindow(CheckFilesChildWindow);
			
			CheckFilesChildWindow.setScene(scene);
			CheckFilesChildWindow.setTitle("Check Files");
			CheckFilesChildWindow.initOwner(mainApp.getMainWindow());
			CheckFilesChildWindow.initModality(Modality.WINDOW_MODAL);
			CheckFilesChildWindow.resizableProperty().setValue(Boolean.FALSE);
			CheckFilesChildWindow.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		}
		
			
	}
	
	@FXML
    private void handleMenuAboutAction(ActionEvent event) {
		
		//System.out.println("menu about selected.");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AboutDialog.fxml"));
		
		Parent root;
			
		try {
						
			root = loader.load();
			Scene scene = new Scene(root);
												
			AboutChildWindow = new Stage();
			
			AboutChildWindow.setScene(scene);
			AboutChildWindow.setTitle("About");
			AboutChildWindow.initOwner(mainApp.getMainWindow());
			AboutChildWindow.initModality(Modality.WINDOW_MODAL);
			AboutChildWindow.resizableProperty().setValue(Boolean.FALSE);
			AboutChildWindow.show();
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		}
		
			
	}
	
	
	@FXML
    private void handleMenuFileSettingsAction(ActionEvent event) {
		
		//System.out.println("menu file setting selected.");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("EditFileSettings.fxml"));
		
		Parent root;
		
	
		try {
						
			root = loader.load();
			Scene scene = new Scene(root);
			
			FileSettingsController controller = loader.getController();
			controller.setMainApp(mainApp);
			
			mainApp.getDatabase().connectDB();
			ArrayList<FileSetting> fileSettings = mainApp.getDatabase().getSettings();
			mainApp.getDatabase().closeDB();
			
			controller.setFileSettings(fileSettings);
			
			mainApp.getFileSettingsObservableList().clear();
			for (FileSetting fs: fileSettings) {
				mainApp.getFileSettingsObservableList().add(fs.settingName);
				
			}
			
			controller.getFileSettingsListView().setItems(mainApp.getFileSettingsObservableList());
			
			/*for (String s : mainApp.getFileSettingsObservableList()) {
				System.out.println(s);
			
			}*/
			
			FileSettingsChildWindow = new Stage();
			controller.setMyWindow(FileSettingsChildWindow);
			
			FileSettingsChildWindow.setScene(scene);
			FileSettingsChildWindow.setTitle("File Settings");
			FileSettingsChildWindow.initOwner(mainApp.getMainWindow());
			FileSettingsChildWindow.initModality(Modality.WINDOW_MODAL);
			FileSettingsChildWindow.resizableProperty().setValue(Boolean.FALSE);
			FileSettingsChildWindow.show();
			
			
			
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		}
		
		
		
		
		
		
	}
	
	@FXML
    private void handleMenuMappingsAction(ActionEvent event) {
		
		//System.out.println("menu mappings selected.");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("EditMappings.fxml"));
		
		Parent root;
		
	
		try {
						
			root = loader.load();
			Scene scene = new Scene(root);
			
			MappingsController controller = loader.getController();
			controller.setMainApp(mainApp);
			
			mainApp.getDatabase().connectDB();
			ArrayList<MappingSetting> mappingSettings = mainApp.getDatabase().getMappingSettings();
			mainApp.getDatabase().closeDB();
			
			controller.setMappingsSettings(mappingSettings);
			
			mainApp.getMappingsSettingsObservableList().clear();
			for (MappingSetting ms: mappingSettings) {
				mainApp.getMappingsSettingsObservableList().add(ms.mappingName);
				
			}
			
			controller.getMappingsListView().setItems(mainApp.getMappingsSettingsObservableList());
			
			/*for (String s : mainApp.getMappingsSettingsObservableList()) {
				System.out.println(s);
			
			}*/
			
			MappingsChildWindow = new Stage();
			controller.setMyWindow(MappingsChildWindow);
			
			MappingsChildWindow.setScene(scene);
			MappingsChildWindow.setTitle("Mappings");
			MappingsChildWindow.initOwner(mainApp.getMainWindow());
			MappingsChildWindow.initModality(Modality.WINDOW_MODAL);
			MappingsChildWindow.resizableProperty().setValue(Boolean.FALSE);
			MappingsChildWindow.show();
			
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load window.").showAndWait();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert(AlertType.WARNING, "Cannot load database.").showAndWait();
			
		}
		
		
		
	}
	
	@FXML
    private void handleMenuCloseAction(ActionEvent event) {
		
		((Stage) mainApp.getMainWindow()).close();
				
	}
	
	@FXML
	private void initialize() {
		
		
		
		
	}

}
