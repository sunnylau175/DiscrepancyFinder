package discrepancyfinder;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainApp extends Application {

	private Settings database = new Settings();
	
	private ObservableList<String> fileSettingsList = FXCollections.observableArrayList();
	private ObservableList<String> mappingsSettingsList = FXCollections.observableArrayList();
	private ObservableList<String> mappingsList = FXCollections.observableArrayList();
	private ObservableList<String> file1SheetsList = FXCollections.observableArrayList();
	private ObservableList<String> file2SheetsList = FXCollections.observableArrayList();
	
	private Thread checkerThread;
	private FilesChecker checker = new FilesChecker();
	
	private Window mainWindow;
	
	private MainController controller;
	
	public void startCheckFiles(FileInfo excelFile1, FileInfo excelFile2, ArrayList<Mapping> mapping, boolean appendRowIndex, boolean appendColumnIndex) {
		
		if (checkerThread == null || !checkerThread.isAlive()) {
			
			controller.getTextAreaResult().clear();
			controller.getLabelStatus().setText("Starting...");
			
			checker.setRequiredElements(controller.getLabelStatus(), controller.getStopButton(), controller.getTextAreaResult(), excelFile1, excelFile2, mapping);
			checker.appendIndexes(appendRowIndex, appendColumnIndex);
			
			checkerThread = new Thread(checker);
			checkerThread.start();
						
		}
	}
	
	public void stopCheckFiles() {
		checker.terminate(true);
				
	}
	
	public Settings getDatabase() {
		return database;
		
	}
	
	public Window getMainWindow() {
		return mainWindow;
		
	}
	
	public ObservableList<String> getFileSettingsObservableList () {
		return fileSettingsList;
		
	}
	
	public ObservableList<String> getMappingsSettingsObservableList () {
		return mappingsSettingsList;
		
	}
	
	public ObservableList<String> getMappingsObservableList () {
		return mappingsList;
		
	}
	
	public ObservableList<String> getFile1SheetsObservableList () {
		return file1SheetsList;
		
	}
	
	public ObservableList<String> getFile2SheetsObservableList () {
		return file2SheetsList;
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("MainApp.fxml"));
		
		mainWindow = primaryStage;
		
		try {
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			controller = loader.getController();
			controller.setMainApp(this);
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Excel Discrepency Finder");
			primaryStage.show();
			
			//System.out.println("Main windows showed.");
								
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			new Alert(AlertType.WARNING, "Cannot load main window.").showAndWait();

		}
	}

	@Override
	public void stop() {
		
		if (checkerThread != null && checkerThread.isAlive()) {
			
			//System.out.println("Try to kill thread...");
			checker.terminate(true);
				
			while (checkerThread.isAlive()) {
				//System.out.println("Waiting for thread to end...");
				
			}
			
			//System.out.println("Thread killed.");
							
		}
		/*else 
			System.out.println("Thread is not alive.");*/
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
